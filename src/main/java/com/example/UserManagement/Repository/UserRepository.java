package com.example.UserManagement.Repository;

import com.example.UserManagement.Model.Role;
import com.example.UserManagement.Model.UserLogin;
import com.example.UserManagement.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {


//    @Query("SELECT u from Users u order by u.modifyDate desc limit 10")
//    List<Users> findAllByModifiedDate();

    @Query(value = "SELECT u from Users u order by u.modifyDate desc")
    List<Users> findAllByModifiedDate(Pageable pageable);

}
