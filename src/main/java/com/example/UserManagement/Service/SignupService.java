package com.example.UserManagement.Service;

import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class SignupService {

    @Autowired
    private UserSignupRepo userSignupRepo;

    public UserSignup addUser(UserSignup user){
        return userSignupRepo.save(user);
    }

    public boolean checkUser(String email){
                if(userSignupRepo.existsById(email))    return true;
        return false;

    }

    public Optional<UserSignup> checkData(@PathVariable String email){
        return userSignupRepo.findById(email);

    }

}
