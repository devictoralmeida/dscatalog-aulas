package com.devsuperior.dscatalog.config.customgrant;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserAuthorities {
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    public String getUsername() {
        return username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
