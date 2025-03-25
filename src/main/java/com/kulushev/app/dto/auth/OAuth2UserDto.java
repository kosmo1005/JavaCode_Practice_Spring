package com.kulushev.app.dto.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public record OAuth2UserDto(
        String id,
        String name,
        String login,
        List<GrantedAuthority> authorities
) implements OAuth2User {

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("id", id, "name", name, "login", login);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }
}
