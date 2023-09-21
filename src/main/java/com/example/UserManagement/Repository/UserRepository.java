package com.example.UserManagement.Repository;

import com.example.UserManagement.Model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {


    @Query(value = "SELECT u from Users u order by u.modifyDate desc")
    List<Users> findAllByModifiedDate(Pageable pageable);

}
