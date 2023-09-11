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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


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

        Users response = userController.insertData(users);

        Assertions.assertEquals(users,response);

    }

    @Test
    public void deleteUser(){

        when(userService.deleteUser("gopinath@gmail.com")).thenReturn("deleted");

        String response = userController.delete("gopinath@gmail.com");

        Assertions.assertEquals("deleted",response);

    }

    @Test
    public void displayAll(){

        List<Users> opt = null;
        Pageable pageable = PageRequest.of(0,10);
        when(userService.findByModified(pageable)).thenReturn(opt);

        List<Users> response = userController.displayPaging(0,10);

        Assertions.assertEquals(null,response);

    }

//    @Test
//    public void displaySingleUserData(){
//
//        Users opt = new Users("shankar@gmail.com","sankar","m",9878787878L, LocalDate.of(2002,9,19),"chennai",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
//
//        Object ResponseEntity = null;
//        when(userService.displayDataFetch("gopinath@gmail.com")).thenReturn(ResponseEntity.ok.body(opt));
//
//        ResponseEntity<Users> response = userController.displayDataFetch("gopinath@gmail.com");
//
//        Assertions.assertEquals(opt,response.getBody());
//
//
//    }


    @Test
    public void displaySingleUserData() {
        Users opt = new Users("shankar@gmail.com", "sankar", "m", 9878787878L, LocalDate.of(2002, 9, 19), "chennai", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));

        ResponseEntity<Users> responseEntity = ResponseEntity.ok().body(opt);

        // Stub the userService to return the ResponseEntity
        when(userService.displayDataFetch("gopinath@gmail.com")).thenReturn(responseEntity);

        ResponseEntity<Users> response = userController.displayDataFetch("gopinath@gmail.com");

        Assertions.assertEquals(opt, response.getBody());
    }



    @Test
    public void checkEmailExists(){

        when(userService.checkEmail("gopinath@gmail.com")).thenReturn(true);

        boolean response = userController.checkEmail("gopinath@gmail.com");

        Assertions.assertEquals(true,response);
    }

    @Test
    public void updateData(){

        Users users = new Users("shankar@gmail.com","sankar","m",9878787878L, LocalDate.of(2002,9,19),"chennai");

        when(userService.insertAfterDataEdited(users)).thenReturn(users);

        Users response = userController.insertAfterEditData(users);

        Assertions.assertEquals(users,response);

    }

}
