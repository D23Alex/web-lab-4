package com.d23alex.lab42.auth;

import com.d23alex.lab42.check.CheckRepository;
import com.d23alex.lab42.check.CheckRequest;
import com.d23alex.lab42.check.Checking;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final UserRepository repository;

    private final CheckRepository checkRepository;

    private final Checking checking;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (repository.findByName(request.getName()).isPresent())
            return ResponseEntity.badRequest().body(new AuthResponse(null));
        return ResponseEntity.ok(service.register(request));
    }

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
