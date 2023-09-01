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

  public ResponseEntity<?> findUserById(Integer id) {
    try {
      User user = userRepository.getReferenceById(id);
      UserDTO userDTO = modelMapper.map(user, UserDTO.class);
      return ResponseEntity.ok().body(userDTO);
    } catch (Exception EntityNotFoundException) {
      HttpStatus httpStatus = HttpStatus.NOT_FOUND;
      ErrorMessage errorMessage = new ErrorMessage(
              httpStatus.value(),
              "Unable to find a user with id " + id);

      return ResponseEntity
              .status(httpStatus)
              .body(errorMessage);
    }

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

  public ResponseEntity<?> uploadProfileImage(Integer id, MultipartFile file) {

    try {
      String profileImagePath = this.createPhotoUrl(id, file);
      this.s3Service.putObject(profileImagePath, file.getBytes());
    } catch (IOException e) {
      HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      ErrorMessage errorMessage = new ErrorMessage(
              httpStatus.value(),
              "Internal server error");

      return ResponseEntity
              .status(httpStatus)
              .body(errorMessage);
    } catch (BadContentTypeException e) {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorMessage errorMessage = new ErrorMessage(
              httpStatus.value(),
              e.getMessage());

      return ResponseEntity
              .status(httpStatus)
              .body(errorMessage);
    }

    return ResponseEntity.ok("");
  }

  public ResponseEntity<?> downloadProfileImage(Integer id) {

    User user = this.userRepository.getReferenceById(id);
    String photoUrl = user.getPhotoUrl();
    byte[] photo = this.s3Service.getObject(photoUrl);

    return ResponseEntity.ok(photo);
  }
}
