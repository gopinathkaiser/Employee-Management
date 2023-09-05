package com.example.UserManagement.Controller;

import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class SignupController {

    @Autowired
    private UserSignupRepo userSignupRepo;

    @CrossOrigin
    @PostMapping("/")
    public UserSignup addUser(@RequestBody UserSignup userData){

        return userSignupRepo.save(userData);
    }

//    @CrossOrigin
//    @GetMapping("/data")
//    public List<UserSignup> getUser(){
//        return userSignupRepo.findAll();
//    }

    @CrossOrigin
    @GetMapping("/userExists/{email}")
    public boolean checkUser(@PathVariable String email){
        if(userSignupRepo.existsById(email))    return true;
        return false;
    }

    @CrossOrigin
    @GetMapping ("/{email}")
    public Optional<UserSignup> checkData(@PathVariable String email){
        return userSignupRepo.findById(email);

    }


}
