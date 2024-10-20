package dev.mosaleh.Library.Management.System.controller;


import dev.mosaleh.Library.Management.System.service.AuthenticationService;
import dev.mosaleh.Library.Management.System.dtos.JwtAuthenticationResponse;
import dev.mosaleh.Library.Management.System.dtos.SigninRequest;
import dev.mosaleh.Library.Management.System.dtos.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableWebSecurity
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testSignup() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setEmail("john.doe@example.com");
        signupRequest.setPassword("password123");

        // When
        doNothing().when(authenticationService).signup(signupRequest);

        // Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType("application/json")
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john.doe@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Signed in succsessfully"));

        verify(authenticationService, times(1)).signup(signupRequest);
    }

    @Test
    public void testSignin() throws Exception {
        // Given
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setEmail("john.doe@example.com");
        signinRequest.setPassword("password123");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("413F4429472B4B6250655368566D5970337336763979244226452948404D6351");

        // When
        when(authenticationService.signin(signinRequest)).thenReturn(jwtResponse);

        // Then
        mockMvc.perform(post("/api/auth/signin")
                        .contentType("application/json")
                        .content("{\"email\":\"john.doe@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("413F4429472B4B6250655368566D5970337336763979244226452948404D6351"));

        verify(authenticationService, times(1)).signin(signinRequest);
    }
}
