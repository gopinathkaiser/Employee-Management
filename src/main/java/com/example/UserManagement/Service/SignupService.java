package com.example.UserManagement.Service;

import com.example.UserManagement.DTO.LoginRequestDTO;
import com.example.UserManagement.DTO.SignUpRequestDTO;
import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import com.example.UserManagement.Util.JwtUtils;
import com.example.UserManagement.common.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class SignupService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserSignupRepo userSignupRepo;

    @Autowired
    private JwtUtils jwtUtils;

    public ApiResponse addUser(SignUpRequestDTO signUpRequestDTO,String siteURL) throws MessagingException, InvocationTargetException {
        ApiResponse apiResponse = new ApiResponse();
        UserSignup userSignup = new UserSignup();
        userSignup.setEmail(signUpRequestDTO.getEmail());
        userSignup.setName(signUpRequestDTO.getName());
        userSignup.setPassword(signUpRequestDTO.getPassword());

        String randomCode = RandomStringUtils.randomAlphanumeric(64);
        userSignup.setVerificationCode(randomCode);

        userSignup = userSignupRepo.save(userSignup);


        sendVerificationCode(userSignup,siteURL);
         apiResponse.setData(userSignup);
        String token = jwtUtils.generateJwt(userSignup);
        Map<String,Object> data = new HashMap<>();
        data.put("accessToken", token);
        apiResponse.setData(data);

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
                String token = jwtUtils.generateJwt(response.get());
                Map<String,Object> data = new HashMap<>();
                data.put("accessToken", token);
                apiResponse.setData(data);
            } else {
                apiResponse.setData("Password wrong");
            }
        }catch (Exception e){
            apiResponse.setData("User login failed");
        }

        return apiResponse;
    }

    public void sendVerificationCode(UserSignup userSignup, String siteURL) {
        try {
            String toAddress = userSignup.getEmail();
            String fromAddress = "gopinathkaiser@gmail.com";
            String senderName = "Gopinath";
            String subject = "Please verify your registration";
            String mailContent = "<p>Dear " + userSignup.getName() + ",</p>";
            mailContent += "<p>Please click below link for verification:</p>";

            String verifyEmail = siteURL + "/verify?code=" + userSignup.getVerificationCode();

            mailContent += "<h3><a href=\"" + verifyEmail + "\">VERIFY</a></h3>";
            mailContent += "<p>Thank you<br>The shopme Team</p>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // Enable HTML content

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(mailContent, true); // Enable HTML content

            javaMailSender.send(message);

            System.out.println("Email sent successfully to: " + toAddress);
        } catch(Exception e){
            e.printStackTrace();

        }
    }
}
