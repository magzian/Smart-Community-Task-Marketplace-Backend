package com.magz.backend.user.mapper;

import com.magz.backend.common.entity.User;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.dtos.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public MyUserProfileDto toMyUserProfileDto(User user) {
        MyUserProfileDto dto = new MyUserProfileDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setLocation(user.getLocation());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setDoerRating(user.getDoerRating());
        dto.setRequesterRating(user.getRequesterRating());
        dto.setJoinedAt(user.getJoinedAt());

        return dto;
    }

    public UserProfileDto toUserProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setBio(user.getBio());
        dto.setLocation(user.getLocation());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setDoerRating(user.getDoerRating());
        dto.setRequesterRating(user.getRequesterRating());
        dto.setJoinedAt(user.getJoinedAt());

        return dto;
    }
}
