package com.example.UserManagement.Controller;

import com.example.UserManagement.Model.UserLogin;
import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Repository.UserLoginRepo;
import com.example.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserLoginRepo userLoginRepo;


    @GetMapping("/checkLogin")
    public List<UserLogin> checkData(){
        System.out.println(userLoginRepo.findAll());
        return userLoginRepo.findAll();
    }

    @PostMapping ("/login")
    public UserLogin login(@RequestBody UserLogin users){
//        System.out.println(userLoginRepo.findAll());
        return userLoginRepo.save(users);
    }

}
