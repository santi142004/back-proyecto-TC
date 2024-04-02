package com.BackendTc.TuCafe.security;

import com.BackendTc.TuCafe.model.Admin;
import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Data
@Component
public class TokenUtils {

    private final static  String ACCESS_TOKEN_SECRET = "4qhq8LrEBfTcaRHxhdb9zURb2rf8e7Ud";
    private final static  Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public String getTokenClient(Client client) {
        return createToken(client.getName(), client.getEmail(), client.getRole(), client.getId_client());
    }

    public String getTokenBusiness(Business business) {
        return createToken(business.getName(), business.getEmail(), business.getRole(), business.getId_business());
    }

    public String getTokenAdmin(Admin admin) {
        return createToken(admin.getName(), admin.getEmail(), admin.getRole(), admin.getId_admin());
    }

    public static String createToken(String nombre, String email, String rol, Long id){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("id", id);
        extra.put("nombre", nombre);
        extra.put("rol", rol);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

        }catch (JwtException e){
            return null;
        }
    }




}
