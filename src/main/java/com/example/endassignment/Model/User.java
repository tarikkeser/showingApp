package com.example.endassignment.Model;

public class User {
    private String userName;
    private String password;
    private Role role;

    public enum Role {
        management, service
    }

    public User(String userName, String password, Role role) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setRole(role);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
