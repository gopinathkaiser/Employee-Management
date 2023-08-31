package com.example.fullstackapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private final UserRepository userRepository;



    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Register");
        return modelAndView;
    }

    @GetMapping("/Edit")
    public ModelAndView Edit(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Register");
        return modelAndView;
    }


//    @GetMapping("/register")
//    public String register(){
//        return "Register";
//    }


    @PostMapping("/insert")
    public Users insertData(@RequestBody Users user){
//        System.out.println(new Timestamp(new Date().getTime()));
        Timestamp ts = new Timestamp(new Date().getTime());
        user.setModifyDate(ts);
        user.setCreateDate(ts);
        return userRepository.save(user);

    }

    @GetMapping("/displayData")
    public List<Users> display(){
        System.out.println(userRepository.findAllByModifiedDate());
        return (List<Users>) userRepository.findAllByModifiedDate();
    }

    @DeleteMapping("/delete/{email}")
    public String delete(@PathVariable String email){
        if(userRepository.existsById(email)){
            userRepository.deleteById(email);
            return "failed";
        }
        return "success";
    }

    @PutMapping("/updateData/{email}")
    public Optional<Users> updateData(@PathVariable String email){
        if(userRepository.existsById(email)){
            return  userRepository.findById(email);
        }

        return null;
    }

    @GetMapping("/editDataFetch/{email}")
    public Optional<Users> displayDataFetch(@PathVariable String email){
        System.out.println(userRepository.findAll());
        return userRepository.findById(email);
    }

    @PostMapping("/insertEditedData")
    public Users insertAfterEditData(@RequestBody Users user){
        Optional<Users> users = userRepository.findById(user.getEmail());
        Timestamp ts = new Timestamp(new Date().getTime());

        Users userData = users.get();

        userData.setFname(user.getFname());
        userData.setLname(user.getLname());
        userData.setMobile(user.getMobile());
        userData.setDob(user.getDob());
        userData.setAddress(user.getAddress());
        userData.setModifyDate(ts);
        userRepository.save(userData);
//        System.out.println("USer from db" + users );

        return user;
    }

    @GetMapping("/checkEmail/{email}")
    public boolean checkEmail(@PathVariable String email){
        if(userRepository.existsById(email)){
            return true;
        }

        return false;
    }
}
