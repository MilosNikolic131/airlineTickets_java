package com.airlineTickets.auth;

import com.airlineTickets.dao.AirlinePersonDataAccessService;
import com.airlineTickets.model.User;
import com.airlineTickets.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final AirlinePersonDataAccessService airlinePersonDataAccessService;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User(UUID.randomUUID(), request.getName(), request.getType(), passwordEncoder.encode(request.getPassword()));
        airlinePersonDataAccessService.insertUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(), request.getPassword()
                )
        );

        var user = airlinePersonDataAccessService.getUserByName(request.getName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
