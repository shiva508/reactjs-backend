package com.comrade.reactjsbackend.service.security;

import com.comrade.reactjsbackend.config.properties.SecurityProperties;
import com.comrade.reactjsbackend.model.AuthUser;
import com.comrade.reactjsbackend.model.LoginResponse;
import com.comrade.reactjsbackend.model.exception.JwtCustomException;
import com.comrade.reactjsbackend.model.exception.RecordNotFoundException;
import com.comrade.reactjsbackend.util.DhaConstants;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtService {

    @Autowired
    private SecurityProperties securityProperties;

    public String tokenGenerator(AuthUser authUser){
        try {
            Claims claims = Jwts.claims().setSubject(authUser.getEmail());
            var roles = authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            claims.putAll(Map.of(DhaConstants.FIRST_NAME,authUser.getFirstName(),
                                 DhaConstants.LAST_NAME,authUser.getLastName(),
                                 DhaConstants.EMAIL,authUser.getEmail(),
                                 DhaConstants.USER_NAME,authUser.getUsername(),
                                 DhaConstants.ROLES,roles));
            long currentTime = System.currentTimeMillis();
            Date tokenExpirationDuration = new Date(currentTime + TimeUnit.MINUTES.toMillis(securityProperties.getExpirationDuration()));
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(currentTime))
                    .setExpiration(tokenExpirationDuration)
                    .signWith(generateSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception exception){
            throw new RecordNotFoundException(exception.getMessage());
        }
    }

    public JwtParser jwtTokenParser(){
        return Jwts.parserBuilder().setSigningKey(generateSignInKey()).build();
    }

    public Claims claimsParser(String jwtToken){
        return jwtTokenParser().parseClaimsJws(jwtToken)
                               .getBody();
    }

    public String tokenExtractor(HttpServletRequest request){
        String jwtToken = request.getHeader(securityProperties.getTokenHeader());
        if (null != jwtToken  && jwtToken.startsWith(securityProperties.getTokenPrefix())){
            return jwtToken.substring(securityProperties.getTokenPrefix().length());
        }
        return null;
    }

    public Claims claimsExtractor(HttpServletRequest request){
        try {
            String token = tokenExtractor(request);
            if(null != token){
                return claimsParser(token);
            }
            return null;
        } catch (Exception exception){
            throw new JwtCustomException(exception.getMessage());
        }
    }

    public boolean validateClaim(Claims claims){
        try {
            return claims.getExpiration().after(new Date(System.currentTimeMillis()));
        }catch (Exception exception){
            throw new JwtCustomException(exception.getMessage());
        }
    }

    public String extractEmail(String jwtToken){
        return claimsParser(jwtToken).getSubject();
    }

    public List<String> extractRoles(Claims claims){
        return (List<String>) claims.get("roles");
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            jwtTokenParser().parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException ex){
            throw new JwtCustomException("Invalid JWT Signature");
        } catch (MalformedJwtException ex){
            throw new JwtCustomException("Invalid JWT Token");
        } catch (ExpiredJwtException ex){
            throw new JwtCustomException("Expired JWT token");
        } catch (UnsupportedJwtException ex){
            throw new JwtCustomException("Unsupported JWT token");
        } catch (IllegalArgumentException | JwtException ex){
            throw new JwtCustomException("JWT claims string is empty");
        }
    }

    public Key generateSignInKey() {
        return new SecretKeySpec(Base64.getDecoder()
                                       .decode(securityProperties.getSecretKey()), SignatureAlgorithm.HS256.getJcaName());
    }

    public LoginResponse buildLoginResponse(String jwtToken){
        Claims claims = claimsParser(jwtToken);
        return LoginResponse.builder()
                .expiresAt(claims.getExpiration())
                .issuedAt(claims.getIssuedAt())
                .roles((List<String>) claims.get(DhaConstants.ROLES))
                .build();
    }

}
