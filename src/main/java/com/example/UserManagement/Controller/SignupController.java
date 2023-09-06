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

    @PostMapping("/")
    public UserSignup addUser(@RequestBody UserSignup userData){

        return this.signupService.addUser(userData);
    }

    @GetMapping("/userExists/{email}")
    public boolean checkUser(@PathVariable String email){

        return this.signupService.checkUser(email);
    }

    @GetMapping ("/{email}")
    public Optional<UserSignup> checkData(@PathVariable String email){
        return this.signupService.checkData(email);

    }


}
