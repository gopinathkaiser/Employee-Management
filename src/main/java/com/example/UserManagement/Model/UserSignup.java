package com.example.UserManagement.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserSignup {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserSignup{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    private String email;

    private String name;

    private String password;
}
