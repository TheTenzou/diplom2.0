package ru.thetenzou.tsoddservice.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {

    static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInSeconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public Authentication getAuthentication(String token) {

        List<GrantedAuthority> authorities = getUserAuthority(token);

        return new UsernamePasswordAuthenticationToken(getUserID(token), "", authorities);
    }

    public String getUserID(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<GrantedAuthority> getUserAuthority(String token) {

        var claimsBody = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        var roles = claimsBody.get("roles");
        // TODO fix geting roles from request
        List<String> rolesList = (List<String>) roles;

        return rolesList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;

        } catch (JwtException | IllegalArgumentException e) {
           logger.error("Jwt invalid or expired token"); 
        }
        return false;
    }
}
