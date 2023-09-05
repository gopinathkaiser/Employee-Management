package com.example.UserManagement.api.Service;

import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Repository.UserRepository;
import com.example.UserManagement.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach()
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addUser(){

        Users users = new Users();

        when(userRepository.save(users)).thenReturn(users);

        Users response = userService.addUser(users);

        Assertions.assertEquals(users,response);
    }

    @Test
    public void deleteUser(){


        String reponse = userService.deleteUser("gopinath@gmail.com");

        Assertions.assertEquals("deleted",reponse);
    }

    @Test
    public void displayAll(){

        List<Users> opt = null;

        when(userRepository.findAllByModifiedDate()).thenReturn(opt);

        List<Users> response = userService.findByModified();

        Assertions.assertEquals(null,response);

    }

    @Test
    public void displaySingleUserData(){

        Optional<Users> opt = null;

        when(userRepository.findById("gopinath@gmail.com")).thenReturn(null);

        Optional<Users> response = userService.displayDataFetch("gopinath@gmail.com");

        Assertions.assertEquals(null,response);
    }

    @Test
    public void checkEmailExists(){

        when(userRepository.existsById("gopinath@gmail.com")).thenReturn(true);

        boolean response = userService.checkEmail("gopinath@gmail.com");

        Assertions.assertEquals(true,response);
    }



}
