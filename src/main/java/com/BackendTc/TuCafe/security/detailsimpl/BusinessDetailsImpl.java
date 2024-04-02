package com.BackendTc.TuCafe.security.detailsimpl;

import com.BackendTc.TuCafe.model.Business;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class BusinessDetailsImpl implements UsersDetails{

    private final Business business;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return business.getPassword();
    }

    @Override
    public String getUsername() {
        return business.getEmail();
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
        return business.getName();
    }

    public String getRole(){
        return business.getRole();
    }
    public Long getId(){
        return business.getId_business();
    }


}
