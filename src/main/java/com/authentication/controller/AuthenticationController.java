package com.authentication.controller;

import com.authentication.dto.AuthenticationRequest;
import com.authentication.dto.AuthenticationResponse;
import com.authentication.dto.RegistrationRequest;
import com.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Artur Tomeyan
 * @date 03/11/2022
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService userService;

    @Autowired
    public AuthenticationController(AuthenticationService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerRequest(@Valid @RequestBody RegistrationRequest registrationRequest) {

        userService.save(registrationRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = userService.login(authenticationRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        userService.logout(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refresh(HttpServletRequest request, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = userService.refreshToken(request, response);

        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}