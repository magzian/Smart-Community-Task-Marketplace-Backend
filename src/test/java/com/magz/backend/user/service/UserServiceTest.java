package com.magz.backend.user.service;

import com.magz.backend.common.entity.User;
import com.magz.backend.common.repository.UserRepository;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void testGetCurrentUserProfile() {
        // Given
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);

        MyUserProfileDto dto = new MyUserProfileDto();
        dto.setEmail(email);
        dto.setName("Test User");
        dto.setLocation("Nairobi");

        // Mock Spring Security context
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(authentication.getName()).thenReturn(email);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock repository and mapper
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toMyUserProfileDto(user)).thenReturn(dto);

        // When
        MyUserProfileDto result = userService.getCurrentUserProfile();

        // Then
        System.out.println("✅ Email from DTO: " + result.getEmail());
        System.out.println("✅ Full DTO: " + result);

        assertEquals(email, result.getEmail());

        // Optional verifications
        verify(userRepository).findByEmail(email);
        verify(userMapper).toMyUserProfileDto(user);
    }
}
