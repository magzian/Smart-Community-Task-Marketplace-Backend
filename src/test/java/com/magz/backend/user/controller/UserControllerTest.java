package com.magz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magz.backend.TestSecurityConfig;
import com.magz.backend.user.config.JacksonConfig;
import com.magz.backend.user.dtos.MyUserProfileDto;
import com.magz.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@Import({TestSecurityConfig.class, JacksonConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "ian@example.com", roles = "USER")
    void testGetCurrentUserProfile_Returns200AndJson() throws Exception {
        // Arrange
        MyUserProfileDto dto = new MyUserProfileDto();
        dto.setId(1L);
        dto.setName("Ian");
        dto.setEmail("ian@example.com");
        dto.setBio("Test bio");
        dto.setLocation("Nairobi");
        dto.setDoerRating(4.5);
        dto.setRequesterRating(4.8);
        dto.setJoinedAt(LocalDateTime.of(2025, 6, 24, 18, 0));
        dto.setSkills(List.of("Java", "Spring"));

        // Mocking service layer
        Mockito.when(userService.getCurrentUserProfile()).thenReturn(dto);

        // Act + Assert
        mockMvc.perform(get("/api/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("ian@example.com"))
                .andExpect(jsonPath("$.name").value("Ian"))
                .andExpect(jsonPath("$.skills").isArray());
    }
}
