package com.kulushev.app.entity;

import com.kulushev.app.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "my_user")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "hash_of_password", nullable = false)
    private String hashOfPassword;

    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;

    @Column(name = "count_of_failed_auth")
    private int countOfFailedAuth;

    @Column(name = "account_locked")
    private boolean accountLocked;

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return hashOfPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}