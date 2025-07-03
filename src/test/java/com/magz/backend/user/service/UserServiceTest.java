package com.magz.backend.user.service;

import com.magz.backend.common.entity.User;
import com.magz.backend.common.repository.UserRepository;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User existingUser;
    private MyUserProfileDto inputDto;
    private MyUserProfileDto expectedDto;

    @BeforeEach
    void setUp() {
        // Setup existing user
        existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setLocation("New York");
        existingUser.setBio("Software Developer");
        existingUser.setEmail("john@example.com");

        // Setup input DTO
        inputDto = new MyUserProfileDto();
        inputDto.setName("Jane Smith");
        inputDto.setLocation("San Francisco");
        inputDto.setBio("Senior Developer");
        inputDto.setEmail("jane@example.com");

        // Setup expected return DTO
        expectedDto = new MyUserProfileDto();
        expectedDto.setName("Jane Smith");
        expectedDto.setLocation("San Francisco");
        expectedDto.setBio("Senior Developer");
        expectedDto.setEmail("jane@example.com");
    }




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

        when(authentication.getName()).thenReturn(email);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock repository and mapper
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toMyUserProfileDto(user)).thenReturn(dto);

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


    @Test
    void updateCurrentUserProfile_WhenUserExists_ShouldUpdateAllFields(){
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);  //Needed to simulate database save
        when(userMapper.toMyUserProfileDto(existingUser)).thenReturn(expectedDto);  //Needed to simulate DTO conversion


        //Act
        MyUserProfileDto result = userService.updateCurrentUserProfile(id, inputDto);


        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getLocation(), result.getLocation());
        assertEquals(expectedDto.getBio(), result.getBio());
        assertEquals(expectedDto.getEmail(), result.getEmail());

        // Verify user fields were updated
        assertEquals("Jane Smith", existingUser.getName());
        assertEquals("San Francisco", existingUser.getLocation());
        assertEquals("Senior Developer", existingUser.getBio());
        assertEquals("jane@example.com", existingUser.getEmail());

        // Verify interactions
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(existingUser);
        verify(userMapper, times(1)).toMyUserProfileDto(existingUser);
    }


    @Test
    void updateCurrentUserProfile_WhenUserNotFound_ShouldThrowUsernameNotFoundException(){
        //Arrange
        Long id = 999L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());


        //Act and assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.updateCurrentUserProfile(id, inputDto)
        );

        assertEquals("User was not found", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).toMyUserProfileDto(any(User.class));


    }

    @Test
    void updateCurrentUserProfile_WhenPartialUpdate_ShouldUpdateOnlyNonNullFields(){
        //Arrange
        Long id = 1L;
        MyUserProfileDto partialDto = new MyUserProfileDto();
        partialDto.setName("Updated Name");
        partialDto.setLocation("Updated Location");
        //bio and email are null - should not be updated

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(userMapper.toMyUserProfileDto(existingUser)).thenReturn(expectedDto);

        //Act
        userService.updateCurrentUserProfile(id, partialDto);

        //Assert - only name and location should be updated
        assertEquals("Updated Name", existingUser.getName());
        assertEquals("Updated Location", existingUser.getLocation());
        assertEquals("Software Developer", existingUser.getBio());
        assertEquals("john@example.com", existingUser.getEmail());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(existingUser);
        verify(userMapper, times(1)).toMyUserProfileDto(existingUser);
    }

    @Test
    void updateCurrentUserProfile_WhenOnlyOneFieldUpdated_ShouldUpdateOnlyThatField(){
        //Arrange
        Long id = 1L;
        MyUserProfileDto singleFieldDto = new MyUserProfileDto();
        singleFieldDto.setBio("Updated Bio Only");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(userMapper.toMyUserProfileDto(existingUser)).thenReturn(expectedDto);

        //Act
        userService.updateCurrentUserProfile(id, singleFieldDto);

        //Assert - only bio is updated
        assertEquals("John Doe", existingUser.getName());
        assertEquals("New York", existingUser.getLocation());
        assertEquals("Updated Bio Only", existingUser.getBio());
        assertEquals("john@example.com", existingUser.getEmail());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(existingUser);
        verify(userMapper, times(1)).toMyUserProfileDto(existingUser);

    }

    @Test
    void updateCurrentUserProfile_WhenRepositorySaveReturnsUpdatedUser_ShouldMapCorrectly() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Jane Smith");
        updatedUser.setLocation("San Francisco");
        updatedUser.setBio("Senior Developer");
        updatedUser.setEmail("jane@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toMyUserProfileDto(updatedUser)).thenReturn(expectedDto);

        // Act
        MyUserProfileDto result = userService.updateCurrentUserProfile(userId, inputDto);

        // Assert
        assertNotNull(result);
        verify(userMapper, times(1)).toMyUserProfileDto(updatedUser);
    }
}

