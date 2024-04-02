package com.BackendTc.TuCafe.security.jwtauthentication;

import com.BackendTc.TuCafe.exceptions.CustomAuthenticationException;
import com.BackendTc.TuCafe.security.AuthCredentials;
import com.BackendTc.TuCafe.security.TokenUtils;
import com.BackendTc.TuCafe.security.detailsimpl.BusinessDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;


public class JWTAuthenticationFilterBusiness extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        AuthCredentials authCredentials = new AuthCredentials();

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch(IOException e){
            throw new CustomAuthenticationException("Error al leer los datos de Autenticacion", e);
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        BusinessDetailsImpl businessImpl = (BusinessDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(businessImpl.getNombre(), businessImpl.getUsername(), businessImpl.getRole(), businessImpl.getId());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
