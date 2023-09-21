package com.example.UserManagement.Repository;

import com.example.UserManagement.Model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepo extends JpaRepository<UserLogin, String> {

}
