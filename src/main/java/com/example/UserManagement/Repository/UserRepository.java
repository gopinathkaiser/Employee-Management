package com.example.UserManagement.Repository;

import com.example.UserManagement.Model.UserLogin;
import com.example.UserManagement.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {

    @Query("SELECT u from Users u order by u.modifyDate desc limit 10")
    List<Users> findAllByModifiedDate();

    Page<Users> findAll(Pageable pageable);

//    long countAll;

}