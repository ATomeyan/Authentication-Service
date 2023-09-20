package com.authentication.service.impl;

import com.authentication.config.jwt.JwtProvider;
import com.authentication.dto.AuthenticationRequest;
import com.authentication.dto.AuthenticationResponse;
import com.authentication.dto.RegistrationRequest;
import com.authentication.entity.Role;
import com.authentication.entity.Roles;
import com.authentication.entity.User;
import com.authentication.exception.AlreadyExistException;
import com.authentication.exception.ObjectInvalidException;
import com.authentication.mapper.UserMapper;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import com.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Artur Tomeyan
 * @date 03/11/2022
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider provider;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder, JwtProvider provider, UserMapper userMapper,
                                     AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.provider = provider;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public void save(RegistrationRequest request) {

        if (request == null) {
            throw new ObjectInvalidException("The registration request is not valid");
        }

        Optional<User> byUsername = userRepository.findByUsername(request.getUsername());
        if (byUsername.isPresent()) {
            throw new AlreadyExistException("The user by this username already exist");
        }

        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            throw new AlreadyExistException("The user by this email already exist");
        }

        Role role = roleRepository.findByName(Roles.USER.name());

        User user = userMapper.mapUserRequestDtoToEntity(role, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        if (request == null) {
            throw new ObjectInvalidException("The authentication request is not valid");
        }

        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (user != null && (user.isEnabled())) {
                String token = provider.accessTokenGenerator(user);
                String refreshToken = provider.refreshTokenGenerator(user);

                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                }

                return new AuthenticationResponse(token, refreshToken);

        }

        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }

        String refreshToken = header.substring(7);
        String username = provider.getUserNameFromJwtToken(refreshToken);
        if (username != null) {
            User user = userRepository.findByUsername(username).orElseThrow();

            if (provider.validateToken(refreshToken)) {
                String token = provider.accessTokenGenerator(user);

                return AuthenticationResponse.builder().accessToken(token).build();
            }
        }
        return null;
    }
}