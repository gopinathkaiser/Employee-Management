package com.example.UserManagement.Controller;

import com.beust.ah.A;
import com.example.UserManagement.DTO.LoginRequestDTO;
import com.example.UserManagement.DTO.SignUpRequestDTO;
import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Service.SignupService;
import com.example.UserManagement.Util.JwtUtils;
import com.example.UserManagement.common.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addUser(@RequestBody SignUpRequestDTO signUpRequestDTO,HttpServletResponse response){
        String password = signUpRequestDTO.getPassword();
        String saltedPassword = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password,saltedPassword);
        signUpRequestDTO.setPassword(hashedPassword);
//        UserSignup user = signupService.addUser(signupService);
        ApiResponse apiResponse = signupService.addUser(signUpRequestDTO,response);

        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/userExists/{email}")
    public boolean checkUser(@PathVariable String email){

        return signupService.checkUser(email);
    }

    @PostMapping ("/validate")
    public ResponseEntity<ApiResponse> checkData(@RequestBody LoginRequestDTO loginRequestDTO){

        System.out.println("login ");
        ApiResponse apiResponse = signupService.loginValidate(loginRequestDTO);

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }

    @GetMapping("/private")
    public ResponseEntity<ApiResponse> privateApi(@RequestHeader(value = "authorization",defaultValue = "") String auth) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
//        String auth1 = "";
//        jwtUtils.verify(auth);

        apiResponse.setData("Private response");
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);

    }


}
