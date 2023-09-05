package com.sbellali.soccerFive.service;

import com.sbellali.soccerFive.dto.ErrorMessage;
import com.sbellali.soccerFive.dto.UserDTO;
import com.sbellali.soccerFive.exception.BadContentTypeException;
import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.repository.UserRepository;

import java.io.IOException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private S3Service s3Service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    public UserDTO findUserById(Integer id) {
        User user = userRepository.getReferenceById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    public String createPhotoUrl(Integer id, MultipartFile photo) {
        System.out.println("Type de fichier : " + photo.getContentType());
        String contentType = photo.getContentType();
        if (contentType == null || !contentType.startsWith("image/"))
            throw new BadContentTypeException(contentType);

        String profileImagePath = "user/" + id + "/" + UUID.randomUUID().toString() + photo.getOriginalFilename();
        User user = userRepository.getReferenceById(id);
        user.setPhotoUrl(profileImagePath);
        this.userRepository.save(user);

        return profileImagePath;
    }

    public void uploadProfileImage(Integer id, MultipartFile file) throws IOException {
        String profileImagePath = this.createPhotoUrl(id, file);
        this.s3Service.putObject(profileImagePath, file.getBytes());
    }

    public byte[] downloadProfileImage(Integer id) {

        User user = this.userRepository.getReferenceById(id);
        String photoUrl = user.getPhotoUrl();
        return this.s3Service.getObject(photoUrl);
    }
}
