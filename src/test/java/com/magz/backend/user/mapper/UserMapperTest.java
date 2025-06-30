package com.magz.backend.user.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.magz.backend.common.entity.User;
import com.magz.backend.user.dtos.MyUserProfileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testMyUserProfileDto(){
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setBio("This is a bio");
        user.setLocation("Nairobi");
        user.setDoerRating(4.5);
        user.setRequesterRating(4.8);
        user.setJoinedAt(LocalDateTime.now());

        MyUserProfileDto dto = userMapper.toMyUserProfileDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getBio(), dto.getBio());

    }

    @Test
    void testObjectMapperSerialization() throws Exception {
        LocalDateTime now = LocalDateTime.of(2025, 6, 24, 18, 0);
        String json = objectMapper.writeValueAsString(now);
        System.out.println("Serialized LocalDateTime: " + json);
    }
}
