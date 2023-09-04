package com.example.UserManagement.Controller;

import com.example.UserManagement.Repository.UserRepository;
import com.example.UserManagement.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
//import com.example.fullstackapplication.WebConfig;

import java.sql.Timestamp;
import java.util.*;




@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserRepository userRepository;

//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    @GetMapping("/")
//    public ModelAndView home(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//        return modelAndView;
//    }

//    @GetMapping("/register")
//    public ModelAndView register(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("Register");
//        return modelAndView;
//    }

//    @GetMapping("/Edit")
//    public ModelAndView Edit(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("Register");
//        return modelAndView;
//    }



    @PostMapping
    public Users insertData(@RequestBody Users user){
//        System.out.println(new Timestamp(new Date().getTime()));
        Timestamp ts = new Timestamp(new Date().getTime());
        user.setModifyDate(ts);
        user.setCreateDate(ts);
        return userRepository.save(user);

    }
    @GetMapping
    public List<Users> display(){
        System.out.println(userRepository.findAllByModifiedDate());
        return (List<Users>) userRepository.findAllByModifiedDate();
    }

    @DeleteMapping("/{email}")
    public String delete(@PathVariable String email){
        if(userRepository.existsById(email)){
            userRepository.deleteById(email);
            return "failed";
        }
        return "success";
    }

//    @CrossOrigin
//    @PutMapping("/updateData/{email}")
//    public Optional<Users> updateData(@PathVariable String email){
//        if(userRepository.existsById(email)){
//            return  userRepository.findById(email);
//        }
//
//        return null;
//    }

    @GetMapping("/{email}")
    public Optional<Users> displayDataFetch(@PathVariable String email){
        System.out.println(userRepository.findAll());
        return userRepository.findById(email);
    }

    @PutMapping
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


    @GetMapping("/verify/{email}")
    public boolean checkEmail(@PathVariable String email){
        if(userRepository.existsById(email)){
           System.out.println("Ia ma alive");
            return true;
        }
        return false;
    }
}
