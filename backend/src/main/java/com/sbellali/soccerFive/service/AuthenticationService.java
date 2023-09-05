package com.sbellali.soccerFive.service;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbellali.soccerFive.dto.LoginResponseDTO;
import com.sbellali.soccerFive.dto.UserDTO;
import com.sbellali.soccerFive.exception.UserAlreadyExistsException;
import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.model.Role;
import com.sbellali.soccerFive.repository.RoleRepository;
import com.sbellali.soccerFive.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    public LoginResponseDTO loginUser(String username, String password) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User currentUser = (User) auth.getPrincipal();
        String token = tokenService.generateToken(currentUser);
        return new LoginResponseDTO(modelMapper.map(currentUser, UserDTO.class), token);
    }

    public UserDTO registerUser(String username, String email, String password) {
        if (this.isUserAlreadyExists(username, email)) {
            throw new UserAlreadyExistsException();
        }

        User newUser = userRepository.save(this.createUser(username, email, password));
        return modelMapper.map(newUser, UserDTO.class);

    }

    private User createUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return new User(username, email, encodedPassword, authorities);
    }

    private boolean isUserAlreadyExists(String username, String email) {
        return (this.userRepository.existsByUsername(username) || this.userRepository.existsByEmail(email));
    }

}
