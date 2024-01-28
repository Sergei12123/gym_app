package com.example.learn_spring_core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User extends BaseEntity implements UserDetails {

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "user_name")
    protected String userName;

    @Column(name = "password")
    protected String password;

    @Column(name = "is_active")
    protected Boolean isActive;
    protected User(String userName) {
        this.userName = userName;
    }
    protected User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected User(String userName, String firstName, String lastName, boolean isActive) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return super.equals(o) &&
            Objects.equals(firstName, user.firstName) &&
            Objects.equals(lastName, user.lastName) &&
            Objects.equals(userName, user.userName) &&
            Objects.equals(password, user.password) &&
            Objects.equals(isActive, user.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, password, isActive);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public String getUserName() {
        return this.userName;
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

}
