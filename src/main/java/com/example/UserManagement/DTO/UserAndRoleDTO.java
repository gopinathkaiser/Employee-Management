package com.example.UserManagement.DTO;

import com.example.UserManagement.Model.Role;
import com.example.UserManagement.Model.Users;

public class UserAndRoleDTO {

    private Users users;

    private Role role;

    public Users getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "UserAndRoleDTO{" +
                "users=" + users +
                ", role='" + role + '\'' +
                '}';
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Role getRoleName() {
        return role;
    }

    public void setRoleName(Role role) {
        this.role = role;
    }
}
