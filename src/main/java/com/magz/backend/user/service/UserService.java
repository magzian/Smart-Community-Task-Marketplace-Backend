package com.magz.backend.user.service;


import com.magz.backend.common.entity.User;
import com.magz.backend.common.repository.UserRepository;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.dtos.UserProfileDto;
import com.magz.backend.user.mapper.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public MyUserProfileDto getCurrentUserProfile(){
        //Extract the authenticated email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //Look up the user
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found"));

        System.out.println("getCurrentUserProfile() was called");
        //Convert to DTO
        return userMapper.toMyUserProfileDto(user);



    }
}
