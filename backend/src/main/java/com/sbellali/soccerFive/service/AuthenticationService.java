package com.sbellali.soccerFive.service;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbellali.soccerFive.dto.ErrorMessage;
import com.sbellali.soccerFive.dto.LoginResponseDTO;
import com.sbellali.soccerFive.dto.UserDTO;
import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.model.Role;
import com.sbellali.soccerFive.repository.RoleRepository;
import com.sbellali.soccerFive.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> LoginUser(String username, String password) {
        try {
            Authentication auth = authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            User currentUser = (User) auth.getPrincipal();
            String token = tokenService.generateJwt(auth);
            return ResponseEntity.ok().body(
                            new LoginResponseDTO(modelMapper.map(currentUser, UserDTO.class), token));
        } catch (BadCredentialsException | DisabledException | LockedException e) {
            HttpStatus httpStatus = HttpStatus.FORBIDDEN;
            if (e instanceof DisabledException) {
                httpStatus = HttpStatus.FORBIDDEN;
            }
            if (e instanceof LockedException) {
                httpStatus = HttpStatus.LOCKED;
            }

            return ResponseEntity
                            .status(httpStatus)
                            .body(new ErrorMessage(httpStatus.value(), e.getMessage()));
        }

    }

    public ResponseEntity<?> registerUser(String username, String email, String password) {
        if (this.userRepository.existsByUsername(username) || this.userRepository.existsByEmail(email)) {
            HttpStatus httpStatus = HttpStatus.CONFLICT;
            return ResponseEntity
                            .status(httpStatus)
                            .body(new ErrorMessage(httpStatus.value(), "username or/and email already exit"));
        }
        User newUser = userRepository.save(this.createUser(username, email, password));
        UserDTO newUserDTO = modelMapper.map(newUser, UserDTO.class);
        return ResponseEntity.ok().body(newUserDTO);
    }

    private User createUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return new User(username, email, encodedPassword, authorities);
    }

}
