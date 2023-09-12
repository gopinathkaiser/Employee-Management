package com.example.UserManagement.Service;

import com.example.UserManagement.DTO.UserAndRoleDTO;
import com.example.UserManagement.Exception.ResourceNotFoundException;
import com.example.UserManagement.Exception.RoleNotFoundException;
import com.example.UserManagement.Model.Role;
import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Repository.RoleRepository;
import com.example.UserManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RoleRepository roleRepository;



    public void addUser(UserAndRoleDTO userAndRoleDTO){
            Users users = userAndRoleDTO.getUsers();
            Timestamp ts = new Timestamp(new Date().getTime());
            users.setModifyDate(ts);
            users.setCreateDate(ts);
            Role role = userAndRoleDTO.getUsers().getRole();
            Role role1 = roleRepository.findByRoleName(role.getRoleName());
            if(role1==null){
                 throw new RoleNotFoundException("Role not found");
            }
            users.setRole(role1);
            userRepository.save(users);
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
        return userRepository.existsById(email);
    }

    public long findCount(){

        return userRepository.count();
    }

}
