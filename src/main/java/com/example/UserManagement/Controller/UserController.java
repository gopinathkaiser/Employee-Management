package com.example.UserManagement.Controller;

import com.example.UserManagement.Repository.UserRepository;
import com.example.UserManagement.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserRepository userRepository;


    @PostMapping
    public Users insertData(@RequestBody Users user){
        Timestamp ts = new Timestamp(new Date().getTime());
        user.setModifyDate(ts);
        user.setCreateDate(ts);
        return userRepository.save(user);

    }
    @GetMapping
    public List<Users> display(){
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

    @GetMapping("/{email}")
    public Optional<Users> displayDataFetch(@PathVariable String email){
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
