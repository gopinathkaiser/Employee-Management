package com.example.fullstackapplication;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, String> {
}
