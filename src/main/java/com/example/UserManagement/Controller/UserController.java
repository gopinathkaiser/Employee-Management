package com.example.UserManagement.Controller;

import com.example.UserManagement.DTO.UserAndRoleDTO;
import com.example.UserManagement.Model.Role;
import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<String> insertData(@RequestBody UserAndRoleDTO userAndRoleDTO){

        userService.addUser(userAndRoleDTO);
        return ResponseEntity.ok("saved");

    }

    @DeleteMapping("/{email}")
    public String delete(@PathVariable String email){
         return userService.deleteUser(email);

    }

    @GetMapping("/{email}")
    public ResponseEntity<Users> displayDataFetch(@PathVariable String email){
//        System.out.println(userService.displayDataFetch(email));
        return userService.displayDataFetch(email);
    }

    @PutMapping
    public ResponseEntity<String> insertAfterEditData(@RequestBody UserAndRoleDTO userAndRoleDTO){
         userService.insertAfterDataEdited(userAndRoleDTO);
         return ResponseEntity.ok("Edited successfully");
    }


    @GetMapping("/verify/{email}")
    public boolean checkEmail(@PathVariable String email){
        return userService.checkEmail(email);
    }

    @GetMapping("/{page}/{size}")
    public List<Users> displayPaging(@PathVariable int page, @PathVariable int size){
        Pageable pageable = PageRequest.of(page, size);
        return userService.findByModified(pageable);
    }

    @GetMapping("/countData")
    public long countData(){

        return userService.findCount();
    }
}


