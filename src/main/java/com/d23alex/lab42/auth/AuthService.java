package com.d23alex.lab42.auth;

import com.d23alex.lab42.config.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        final var newbie = new User(request.getName(), passwordEncoder.encode(request.getPassword()), Role.ROLE_USER);
        repository.save(newbie);
        return new AuthResponse(jwtService.generateToken(newbie));
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(), request.getPassword()));
        return new AuthResponse(
                jwtService.generateToken(
                        repository.findByName(
                                request.getName()).orElseThrow()));
    }

    public String username(HttpServletRequest request) {
        return getClaim(
                request.getHeader("Authorization").substring(7),
                Claims::getSubject);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey("645267556B58703273357638792F423F4428472B4B6250655368566D59713374").parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
