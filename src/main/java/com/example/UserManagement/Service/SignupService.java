package com.example.UserManagement.Service;

import com.example.UserManagement.DTO.LoginRequestDTO;
import com.example.UserManagement.DTO.SignUpRequestDTO;
import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import com.example.UserManagement.Util.JwtUtils;
import com.example.UserManagement.common.ApiResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    @Autowired
    private UserSignupRepo userSignupRepo;

    @Autowired
    private JwtUtils jwtUtils;

    public ApiResponse addUser(SignUpRequestDTO signUpRequestDTO){
        ApiResponse apiResponse = new ApiResponse();
        UserSignup userSignup = new UserSignup();
        userSignup.setEmail(signUpRequestDTO.getEmail());
        userSignup.setName(signUpRequestDTO.getName());
        userSignup.setPassword(signUpRequestDTO.getPassword());
        userSignup = userSignupRepo.save(userSignup);
//        return userSignupRepo.save(user);
         apiResponse.setData(userSignup);

        return apiResponse;
    }

    public boolean checkUser(String email){
        if(userSignupRepo.existsById(email))    return true;
            return false;

    }

//    public Optional<UserSignup> checkData(String email){
//        return userSignupRepo.findById(email);
//
//    }

    public ApiResponse loginValidate(LoginRequestDTO loginRequestDTO) {
        ApiResponse apiResponse = new ApiResponse();

        Optional<UserSignup> response;
        try {
            response = userSignupRepo.findById(loginRequestDTO.getEmail());
            if (BCrypt.checkpw(loginRequestDTO.getPassword(), response.get().getPassword())) {
//                apiResponse.setData("User Logged in");
                String token = jwtUtils.generateJwt(response.get());
                apiResponse.setData(token);
            } else {
                apiResponse.setData("Password wrong");
            }
        }catch (Exception e){
            apiResponse.setData("User login failed");
        }

        return apiResponse;
    }
}
