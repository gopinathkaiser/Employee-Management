package com.example.UserManagement.Controller;

import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Repository.UserSignupRepo;
import com.example.UserManagement.Service.SignupService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class SignupController {

    @Autowired
    SignupService signupService;

    @PostMapping("/")
    public UserSignup addUser(@RequestBody UserSignup userData){
        String password = userData.getPassword();
        String saltedPassword = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password,saltedPassword);
        userData.setPassword(hashedPassword);
        return this.signupService.addUser(userData);
    }

    @GetMapping("/userExists/{email}")
    public boolean checkUser(@PathVariable String email){

        return this.signupService.checkUser(email);
    }

    @PostMapping ("/validate")
    public String checkData(@RequestBody UserSignup userSignup){
        Optional<UserSignup> response = this.signupService.checkData(userSignup.getEmail());

        if(BCrypt.checkpw(userSignup.getPassword(),response.get().getPassword())){
            return "success";
        }
        return "failure";

    }

}
