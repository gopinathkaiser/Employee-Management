package com.example.UserManagement.Service;

import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Users addUser(Users user){
        return userRepository.save(user);
    }

    public List<Users> findByModified(){
        return userRepository.findAllByModifiedDate();
    }

    public String deleteUser(String email){
         userRepository.deleteById(email);
         return "deleted";
    }

    public Optional<Users> displayDataFetch(String email){
        System.out.println(userRepository.findById(email));
        return userRepository.findById(email);
    }

    public Users insertAfterDataEdited(Users user){
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

    public boolean checkEmail(String email){
        if(userRepository.existsById(email)){
            System.out.println("Ia ma alive");
            return true;
        }
        return false;
    }


}
