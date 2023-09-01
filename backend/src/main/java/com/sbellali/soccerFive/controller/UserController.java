package com.sbellali.soccerFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbellali.soccerFive.service.UserService;

@RestController
@RequestMapping("user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> registerUser(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping(value = "/{id}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(
                    @PathVariable("id") Integer id,
                    @RequestParam("file") MultipartFile file) {
        return this.userService.uploadProfileImage(id, file);
    }

    @GetMapping(value = "/{id}/profile-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> uploadProfileImage(
                    @PathVariable("id") Integer id) {
        return this.userService.downloadProfileImage(id);
    }
}
