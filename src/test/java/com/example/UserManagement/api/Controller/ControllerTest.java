package com.example.UserManagement.api.Controller;


import com.example.UserManagement.Controller.UserController;
import com.example.UserManagement.Model.Users;
import com.example.UserManagement.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;
//import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insertUser(){

        Users users = new Users();

        when(userService.addUser(users)).thenReturn(users);

        Users reponse = userController.insertData(users);

        Assertions.assertEquals(users,reponse);

    }

    @Test
    public void deleteUser(){

        when(userService.deleteUser("gopinath@gmail.com")).thenReturn("deleted");

        String reponse = userController.delete("gopinath@gmail.com");

        Assertions.assertEquals("deleted",reponse);

    }

    @Test
    public void displayAll(){

        List<Users> opt = null;

        when(userService.findByModified()).thenReturn(opt);

        List<Users> response = userController.display();

        Assertions.assertEquals(null,response);

    }

    @Test
    public void displaySingleUserData(){

        Users opt = new Users("shankar@gmail.com","sankar","m",9878787878L, LocalDate.of(2002,9,19),"chennai",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));

        when(userService.displayDataFetch("gopinath@gmail.com")).thenReturn(Optional.of(opt));

        Optional<Users> response = userController.displayDataFetch("gopinath@gmail.com");

        Assertions.assertEquals(Optional.of(opt),response);
    }

    @Test
    public void checkEmailExists(){

        when(userService.checkEmail("gopinath@gmail.com")).thenReturn(true);

        boolean response = userController.checkEmail("gopinath@gmail.com");

        Assertions.assertEquals(true,response);
    }

   

}
