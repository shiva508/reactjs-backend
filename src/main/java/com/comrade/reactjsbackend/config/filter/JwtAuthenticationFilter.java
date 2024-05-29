package com.comrade.reactjsbackend.config.filter;

import com.comrade.reactjsbackend.model.AuthUser;
import com.comrade.reactjsbackend.service.security.JwtService;
import com.comrade.reactjsbackend.service.security.UserDetailsServiceImpl;
import com.comrade.reactjsbackend.util.DhaConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwtToken = jwtTokenExtractorHttpServletRequest(request);
            log.info("Request received {}",jwtToken);
            if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                log.info("Request received no token ");
                filterChain.doFilter(request, response);
                return;
            }
            if(StringUtils.hasText(jwtToken) && jwtService.validateJwtToken(jwtToken.substring(7))) {
                String email = jwtService.extractEmail(jwtToken.substring(7));
                AuthUser authUser = (AuthUser) userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {

        }
        filterChain.doFilter(request, response);
    }

    public String jwtTokenExtractorHttpServletRequest(HttpServletRequest request) {
        String jwtToken=request.getHeader(DhaConstants.HEADER_STRING);
        if(StringUtils.hasText(jwtToken) && jwtToken.startsWith(DhaConstants.TOKEN_PREFIX)) {
            return jwtToken;
        }else {
            return null;
        }
    }
}
