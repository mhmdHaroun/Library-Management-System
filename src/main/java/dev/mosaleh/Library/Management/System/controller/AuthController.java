package dev.mosaleh.Library.Management.System.controller;

import dev.mosaleh.Library.Management.System.dtos.JwtAuthenticationResponse;
import dev.mosaleh.Library.Management.System.dtos.SigninRequest;
import dev.mosaleh.Library.Management.System.dtos.SignupRequest;
import dev.mosaleh.Library.Management.System.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signUpRequest) {
        authenticationService.signup(signUpRequest);
        return ResponseEntity.ok("Signed in succsessfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest) {
        System.out.println("signin controller is called");
        JwtAuthenticationResponse response = authenticationService.signin(signinRequest);
        return ResponseEntity.ok(response);
    }
}
