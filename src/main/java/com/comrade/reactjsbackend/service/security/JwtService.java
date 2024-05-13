package com.comrade.reactjsbackend.service.security;

import com.comrade.reactjsbackend.config.properties.SecurityProperties;
import com.comrade.reactjsbackend.model.AuthUser;
import com.comrade.reactjsbackend.model.UserProfile;
import com.comrade.reactjsbackend.model.exception.JwtException;
import com.comrade.reactjsbackend.model.exception.RecordNotFoundException;
import com.comrade.reactjsbackend.util.DhaConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtService {

    @Autowired
    private SecurityProperties securityProperties;

    public String tokenGenerator(AuthUser authUser){
        try {
            Claims claims = Jwts.claims().setSubject(authUser.getEmail());
            claims.putAll(Map.of(DhaConstants.FIRST_NAME,authUser.getFirstName(),DhaConstants.LAST_NAME,authUser.getLastName()));
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
        return Jwts.parser().setSigningKey(securityProperties.getSecretKey());
    }

    public Claims claimsParser(String token){
        return jwtTokenParser().parseClaimsJws(token)
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
            throw new JwtException(exception.getMessage());
        }
    }

    public boolean validateClaim(Claims claims){
        try {
            return claims.getExpiration().after(new Date(System.currentTimeMillis()));
        }catch (Exception exception){
            throw new JwtException(exception.getMessage());
        }
    }

    public String extractEmail(String token){
        return claimsParser(token).getSubject();
    }

    public List<String> extractRoles(Claims claims){
        return (List<String>) claims.get("roles");
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            jwtTokenParser().parseClaimsJws(jwtToken);
            return true;
		/*} catch (SignatureException e) {
			//throw new JwtException("Signature Failed");
		}catch (MalformedJwtException e) {
			//throw new JwtException("Mal formed Jwt ");
		}catch (ExpiredJwtException e) {
			//throw new JwtException("Jwt Expired");
		}catch (UnsupportedJwtException e) {
			//throw new JwtException("Jwt Unsupported");
		}catch (IllegalArgumentException e) {
			//throw new JwtException("IllegalArgument");
		}*/
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public Key generateSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
