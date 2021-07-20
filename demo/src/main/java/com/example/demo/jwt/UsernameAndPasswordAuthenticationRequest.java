package com.example.demo.jwt;

public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;
    public UsernameAndPasswordAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UsernameAndPasswordAuthenticationRequest() {

    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
