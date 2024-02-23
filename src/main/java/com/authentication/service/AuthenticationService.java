package com.authentication.service;

import com.authentication.dto.AuthenticationRequest;
import com.authentication.dto.AuthenticationResponse;
import com.authentication.dto.RegistrationRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Artur Tomeyan
 * @date 02/11/2022
 */
public interface AuthenticationService {

    void save(RegistrationRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);

    AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response);
}