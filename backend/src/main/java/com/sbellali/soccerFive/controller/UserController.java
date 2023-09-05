package com.sbellali.soccerFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.sbellali.soccerFive.exception.BadContentTypeException;
import com.sbellali.soccerFive.service.UserService;

@RestController
@RequestMapping("user")
@CrossOrigin("*")
public class UserController extends AbsractController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/{id}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(
            @PathVariable("id") Integer id,
            @RequestParam("file") MultipartFile file) {
        ResponseEntity<?> response;
        try {
            this.userService.uploadProfileImage(id, file);
            response = this.successResponse(HttpStatus.CREATED, "File uploaded successfully");

        } catch (BadContentTypeException e) {
            response = this.errorResponse(HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            response = this.errorResponse();
        }

        return response;
    }

    @GetMapping(value = "/{id}/profile-image", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<?> uploadProfileImage(@PathVariable("id") Integer id) {
        ResponseEntity<?> response;
        try {
            byte[] photo = this.userService.downloadProfileImage(id);
            response = this.successResponse(photo);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @RequestMapping("/")
    public String HelloAdmin() {
        return "User Level";
    }

}
