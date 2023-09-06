package com.example.UserManagement.Repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.UserManagement.Model.UserSignup;
public interface UserSignupRepo extends JpaRepository<UserSignup, String> {
}
