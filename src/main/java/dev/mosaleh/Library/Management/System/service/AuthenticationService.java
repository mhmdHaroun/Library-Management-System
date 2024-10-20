package dev.mosaleh.Library.Management.System.service;


import dev.mosaleh.Library.Management.System.dtos.JwtAuthenticationResponse;
import dev.mosaleh.Library.Management.System.dtos.SigninRequest;
import dev.mosaleh.Library.Management.System.dtos.SignupRequest;
import dev.mosaleh.Library.Management.System.model.User;
import dev.mosaleh.Library.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        System.out.println("signin service is called ");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
        );
        User user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new JwtAuthenticationResponse(token, refreshToken);
    }

    public void signup(SignupRequest signUpRequest) {
        if (!isValidEmail(signUpRequest.getEmail())) {
            System.out.println("invalid Email");
            throw new IllegalArgumentException("Invalid email");
        }
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            System.out.println("email is already in use");
            throw new IllegalArgumentException("Email is already in use");
        }
        if (!isValidPassword(signUpRequest.getPassword())) {
            System.out.println("Use strong password");
            throw new IllegalArgumentException("Password must be at least 8 characters long, contain letters and numbers");
        }

        userRepository.save(User.builder()
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build()
        );
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches(".*\\d.*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}




