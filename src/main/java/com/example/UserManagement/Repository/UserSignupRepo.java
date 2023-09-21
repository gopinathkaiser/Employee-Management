package com.example.UserManagement.Repository;

import com.example.UserManagement.Model.UserSignup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSignupRepo extends JpaRepository<UserSignup, String> {
    UserSignup findByName(String username);


//    Object findByUserName(String userName);
}
