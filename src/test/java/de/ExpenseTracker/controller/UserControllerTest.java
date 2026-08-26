package de.ExpenseTracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ExpenseTracker.Controller.UserController;
import de.ExpenseTracker.dto.RegisterUserData;
import de.ExpenseTracker.exceptions.ErrorCode;
import de.ExpenseTracker.exceptions.InvalidCredentialsException;
import de.ExpenseTracker.exceptions.UserAlreadyExistsException;
import de.ExpenseTracker.model.Users;
import de.ExpenseTracker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean(name = "messageSource")
    private MessageSource messageSource;

    @MockitoBean
    private UserService userService;


    // register
    // positive
    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnSuccessWhenRegisteringUser() throws Exception {

        Users user = Users.builder().userid(UUID.randomUUID()).username("testUser").passwordHash("hashedPassword").createdAt(Instant.now()).build();

        Mockito.when(userService.register(any(RegisterUserData.class), any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(user);

        mockMvc.perform(post("/users/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createRegisterUserData()))).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("testUser"));
    }

    // negative
    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnBadRequestWhenUsernameAlreadyExists() throws Exception {
        Mockito.doThrow(new UserAlreadyExistsException(ErrorCode.USER_EXISTS)).when(userService).register(any(RegisterUserData.class), any(HttpServletRequest.class), any(HttpServletResponse.class));

        Mockito.when(messageSource.getMessage(eq(ErrorCode.USER_EXISTS.name()), any(), any(Locale.class))).thenReturn("Username already exists! Please choose another one!");

        mockMvc.perform(post("/users/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createRegisterUserData()))).andExpect(status().isConflict()).andExpect(jsonPath("$.messageKey").value("USER_EXISTS"));
    }

    @Test
    @WithMockUser(username = "joel")
    void testShouldReturnBadRequestWhenPasswordsDoNotMatch() throws Exception {
        Mockito.when(messageSource.getMessage(eq(ErrorCode.PASSWORD_MISMATCH.name()), any(), any(Locale.class))).thenReturn("Passwords don't match!");

        Mockito.doThrow(new InvalidCredentialsException(ErrorCode.PASSWORD_MISMATCH)).when(userService).register(any(RegisterUserData.class), any(HttpServletRequest.class), any(HttpServletResponse.class));

        mockMvc.perform(post("/users/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createRegisterUserData()))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.messageKey").value("PASSWORD_MISMATCH"));
    }


    // helper methods to create userData Objects
    private RegisterUserData createRegisterUserData() {
        RegisterUserData data = new RegisterUserData();
        data.setUsername("testuser");
        data.setPassword("password123");
        data.setPasswordConfirm("password123");
        return data;
    }
}