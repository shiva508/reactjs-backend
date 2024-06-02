package com.comrade.reactjsbackend.controller;

import com.comrade.reactjsbackend.model.AuthUser;
import com.comrade.reactjsbackend.model.LoginRequest;
import com.comrade.reactjsbackend.model.LoginResponse;
import com.comrade.reactjsbackend.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));
        if (authentication.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            String jwtToken = jwtService.tokenGenerator(authUser);
            LoginResponse loginResponse = jwtService.buildLoginResponse(jwtToken);
            loginResponse.setEmail(authUser.getEmail());
            loginResponse.setUsername(authUser.getUsername());
            loginResponse.setFirstName(authUser.getFirstName());
            loginResponse.setLastName(authUser.getLastName());
            loginResponse.setToken(jwtToken);
            loginResponse.setIsAuthorised(true);
            return loginResponse;
        }
        return null;
    }
}
