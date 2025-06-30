package com.magz.backend.user.service;


import com.magz.backend.common.entity.User;
import com.magz.backend.common.repository.UserRepository;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.dtos.UserProfileDto;
import com.magz.backend.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    @Autowired
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

    public UserProfileDto getUserByProfileId(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Profile not found"));
        return userMapper.toUserProfileDto(user);
    }

    public MyUserProfileDto updateCurrentUserProfile(Long id, MyUserProfileDto dto){
        //get the user by id
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User was not found"));

        // Update only non-null values to allow partial updates
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getLocation() != null) {
            user.setLocation(dto.getLocation());
        }
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        User savedUser = userRepository.save(user);

        return userMapper.toMyUserProfileDto(savedUser);

    }
}
