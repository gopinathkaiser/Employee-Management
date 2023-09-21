package com.example.UserManagement.Service;

import com.example.UserManagement.DTO.LoginRequestDTO;
import com.example.UserManagement.DTO.SignUpRequestDTO;
import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import com.example.UserManagement.Util.JwtUtils;
import com.example.UserManagement.common.ApiResponse;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SignupService {

    @Autowired
    private UserSignupRepo userSignupRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailSenderService emailSenderService;


    public ApiResponse addUser(SignUpRequestDTO signUpRequestDTO, String siteURL) throws MessagingException, InvocationTargetException {
        ApiResponse apiResponse = new ApiResponse();
        UserSignup userSignup = UserSignup.builder()
                                .email(signUpRequestDTO.getEmail())
                                .name(signUpRequestDTO.getName())
                                .password(signUpRequestDTO.getPassword())
                                .build();

        String randomCode = RandomStringUtils.randomNumeric(6);
        userSignup.setVerificationCode(randomCode);
        userSignup = userSignupRepo.save(userSignup);

        emailSenderService.sendVerificationCode(userSignup, siteURL);
        apiResponse.setData(userSignup);

        return apiResponse;
    }


    public boolean checkUser(String email) {

        if (userSignupRepo.existsById(email)) return true;
        return false;

    }


    public ApiResponse loginValidate(LoginRequestDTO loginRequestDTO) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<UserSignup> response;

        try {
            response = userSignupRepo.findById(loginRequestDTO.getEmail());
            if ((response.get().isVerificationStatus())) {
                if (BCrypt.checkpw(loginRequestDTO.getPassword(), response.get().getPassword())) {

                    String token = jwtUtils.generateJwt(response.get());
                    Map<String, Object> data = new HashMap<>();
                    data.put("accessToken", token);
                    apiResponse.setData(data);
                } else {
                    apiResponse.setData("Password wrong");
                }
            } else {
                apiResponse.setData("Verify the email");
            }
        } catch (Exception e) {
            apiResponse.setData("User login failed");
        }

        return apiResponse;
    }


    public ApiResponse verifyDetails(String email, String code) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<UserSignup> optionalUserSignup = userSignupRepo.findById(email);
        UserSignup userSignup = optionalUserSignup.get();

        if (userSignup.getVerificationCode().equals(code)) {
            apiResponse.setData(userSignup);
            return apiResponse;
        }

        apiResponse.setData("Failure");
        return apiResponse;
    }
}
