package com.magz.backend.dto;

public class RegisterUserDto {
    private String email;
    private String password;
    private String name;
    private String roles;

    public RegisterUserDto(String email, String password, String name, String roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
