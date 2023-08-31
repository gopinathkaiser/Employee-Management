package com.example.fullstackapplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    @Query("SELECT u from Users u order by u.modifyDate desc limit 10")
    List<Users> findAllByModifiedDate();


}
