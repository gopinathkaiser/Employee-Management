package com.example.UserManagement.Controller;

import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import com.example.UserManagement.Service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class SignupController {

    @Autowired
    SignupService signupService;

    @CrossOrigin
    @PostMapping("/")
    public UserSignup addUser(@RequestBody UserSignup userData){

//        return userSignupRepo.save(userData);

        return this.signupService.addUser(userData);
    }

//    @CrossOrigin
//    @GetMapping("/data")
//    public List<UserSignup> getUser(){
//        return userSignupRepo.findAll();
//    }

    @CrossOrigin
    @GetMapping("/userExists/{email}")
    public boolean checkUser(@PathVariable String email){
//        if(userSignupRepo.existsById(email))    return true;
//        return false;

        return this.signupService.checkUser(email);
    }

    @CrossOrigin
    @GetMapping ("/{email}")
    public Optional<UserSignup> checkData(@PathVariable String email){
        return this.signupService.checkData(email);

    }


}
