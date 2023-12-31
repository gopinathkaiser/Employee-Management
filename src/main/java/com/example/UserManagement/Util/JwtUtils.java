package com.example.UserManagement.Util;

import com.example.UserManagement.Model.UserSignup;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    Date issuedDate = new Date();
    private static String secret = "This_is_secret";

    public static long expiry = 60 * 60;

    public String generateJwt(UserSignup userSignup) {

        long milli = System.currentTimeMillis();
        long expiryMilli = milli + expiry * 1000;
        Date expiryDate = new Date(expiryMilli);

        Claims claims = Jwts.claims().setIssuer(userSignup.getEmail()).setIssuedAt(issuedDate).setExpiration(expiryDate);
        claims.put("name", userSignup.getName());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public AccessDeniedException verify(String authorization) throws Exception {
        try {
            if (authorization == null) {
                throw new AccessDeniedException("Unauthorized");
            }
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
        } catch (Exception e) {
            throw new AccessDeniedException("Unauthorized");
        }
        return null;
    }
}
