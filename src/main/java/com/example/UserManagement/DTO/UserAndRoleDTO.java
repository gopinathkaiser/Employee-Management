package com.example.UserManagement.DTO;

import com.example.UserManagement.Model.Role;
import com.example.UserManagement.Model.Users;
import lombok.Data;

@Data
public class UserAndRoleDTO {

    private Users users;

    private Role role;


//    @Override
//    public String toString() {
//        return "UserAndRoleDTO{" +
//                "users=" + users +
//                ", role='" + role + '\'' +
//                '}';
//    }

//    public Users getUsers() {
//        return users;
//    }


//    public void setUsers(Users users) {
//        this.users = users;
//    }
//
//    public Role getRoleName() {
//        return role;
//    }
//
//    public void setRoleName(Role role) {
//        this.role = role;
//    }
}
