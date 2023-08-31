package com.sbellali.soccerFive.service;

import com.sbellali.soccerFive.dto.ErrorMessage;
import com.sbellali.soccerFive.dto.UserDTO;
import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

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
}
