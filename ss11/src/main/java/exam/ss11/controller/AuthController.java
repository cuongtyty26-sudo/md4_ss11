package exam.ss11.controller;

import exam.ss11.dto.LoginRequest;
import exam.ss11.dto.LoginResponse;
import exam.ss11.security.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String token = jwtProvider.generateToken(authentication);

        LoginResponse response = new LoginResponse(
                token,
                "Bearer",
                authentication.getName()
        );

        return ResponseEntity.ok(response);
    }
}