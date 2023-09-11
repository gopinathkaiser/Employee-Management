package com.example.UserManagement.Service;

import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    public List<Users> findByModified(Pageable pageable){
        return userRepository.findAllByModifiedDate( pageable);
    }

    public String deleteUser(String email){
         userRepository.deleteById(email);
         return "deleted";
    }

    public ResponseEntity<Users> displayDataFetch(String email){
        Users users = userRepository.findById(email).orElse(new ResourceNotFoundException("exception"));
        ResponseEntity.status(205);
        return ResponseEntity.ok().body(users);
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
            return true;
        }
        return false;
    }

    public long findCount(){

        return userRepository.count();
    }

}
