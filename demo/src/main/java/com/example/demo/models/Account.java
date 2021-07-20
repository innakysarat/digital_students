package com.example.demo.models;

import com.example.demo.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "accounts"
)
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "account_id",
            updatable = false
    )
    private Long account_id;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(
            name = "account_name",
            columnDefinition = "TEXT"
    )
    private String name;
    @Column(
            name = "account_surname",
            columnDefinition = "TEXT"
    )
    private String surname;
    @Column(
            name = "account_patronymic",
            columnDefinition = "TEXT"
    )
    private String patronymic;
    @Column(
            name = "account_email",
            columnDefinition = "TEXT"
    )
    private String email;
    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String username;
    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;
    @Column(
            name = "account_tg",
            columnDefinition = "TEXT"
    )
    private String telegram;
    @Column(
            name = "account_interests",
            columnDefinition = "TEXT"
    )
    private String interests;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }
    )
    @JoinTable(
            name = "accounts_projects",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    Set<Project> likedProjects = new HashSet<>();

    public Long getAccount_id() {
        return account_id;
    }

    public UserRole getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTelegram() {
        return telegram;
    }

    public String getInterests() {
        return interests;
    }

    public Set<Project> getLikedProjects() {
        return likedProjects;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setLikedProjects(Set<Project> likedProjects) {
        this.likedProjects = likedProjects;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
