package com.comrade.reactjsbackend.config.handler;

import com.comrade.reactjsbackend.model.exception.InvalidLoginResponse;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        InvalidLoginResponse loginResponse=new InvalidLoginResponse();
        loginResponse.setMessage(accessDeniedException.getMessage());
        String jsonLoginResponse=new Gson().toJson(loginResponse);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print(jsonLoginResponse);
    }
}
