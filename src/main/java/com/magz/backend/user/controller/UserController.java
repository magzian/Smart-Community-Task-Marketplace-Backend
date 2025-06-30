package com.magz.backend.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.dtos.UserProfileDto;
import com.magz.backend.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Fetch details of currently logged in user
    @GetMapping("/me")
    public ResponseEntity<MyUserProfileDto> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserProfileDto> getUserByProfileId(@PathVariable Long id) {
        UserProfileDto userProfile = userService.getUserByProfileId(id);
        return ResponseEntity.ok(userProfile);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<MyUserProfileDto> updateUserProfile(
            @PathVariable Long id,
            @RequestBody @Valid MyUserProfileDto dto) {
        try {
            MyUserProfileDto updatedProfile = userService.updateCurrentUserProfile(id, dto);
            return ResponseEntity.ok(updatedProfile);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
