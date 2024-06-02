package com.comrade.reactjsbackend.config.entrypoint;

import java.io.IOException;
import java.util.Date;

import com.comrade.reactjsbackend.model.exception.AccessDeniedResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Component
public class JWTAthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        AccessDeniedResponse loginResponse=new AccessDeniedResponse();
        loginResponse.setMessage(authException.getMessage());
        loginResponse.setTimeStamp(new Date());
        loginResponse.setStatusCode(401);
        String jsonLoginResponse=new Gson().toJson(loginResponse);
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print(jsonLoginResponse);
    }

}
