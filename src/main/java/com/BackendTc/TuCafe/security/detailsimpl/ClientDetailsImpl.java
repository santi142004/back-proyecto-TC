package com.BackendTc.TuCafe.security.detailsimpl;

import com.BackendTc.TuCafe.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class ClientDetailsImpl implements UsersDetails {

    private final Client client;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return client.getPassword();
    }

    @Override
    public String getUsername() {
        return client.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre(){
        return client.getName();
    }

    public String getRole(){
        return client.getRole();
    }
    public Long getId(){
        return client.getId_client();
    }
}
