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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service

public class SignupService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserSignupRepo userSignupRepo;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailSender emailSender;


    public ApiResponse addUser(SignUpRequestDTO signUpRequestDTO, String siteURL) throws MessagingException, InvocationTargetException {
        ApiResponse apiResponse = new ApiResponse();


        System.out.println("print 3" + LocalTime.now());
        UserSignup userSignup = UserSignup.builder()
                .email(signUpRequestDTO.getEmail())
                .name(signUpRequestDTO.getName())
                .password(signUpRequestDTO.getPassword())
                .build();

        System.out.println("print 4" + LocalTime.now());
        String randomCode = RandomStringUtils.randomNumeric(6);
        userSignup.setVerificationCode(randomCode);
        userSignup = userSignupRepo.save(userSignup);

        System.out.println("print 5" + LocalTime.now());
        emailSender.sendVerificationCode(userSignup, siteURL);
        apiResponse.setData(userSignup);

        System.out.println("print 6" + LocalTime.now());
        return apiResponse;
    }


    public boolean checkUser(String email) {
        if (userSignupRepo.existsById(email)) return true;

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
            System.out.println(response.get().isVerificationStatus());
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

//    @Async
//    public void sendVerificationCode(UserSignup userSignup, String siteURL) {
//        try {
//            System.out.println("print 11 " + LocalTime.now());
//            String toAddress = userSignup.getEmail();
//            String fromAddress = "gopinathkaiser@gmail.com";
//            String senderName = "Gopinath";
//            String subject = "Please verify your registration";
//            String mailContent = "<p>Dear " + userSignup.getName() + ",</p>";
//            mailContent += "<p>Please click below link for verification:</p>";
//            System.out.println("print 12 " + LocalTime.now());
//            String verifyEmail = siteURL + "/Auth/verify?email=" + userSignup.getEmail() + "&code=" + userSignup.getVerificationCode();
//            System.out.println("print 13 " + LocalTime.now());
//            mailContent += "<h3><a href=\"" + verifyEmail + "\">VERIFY</a></h3>";
//            mailContent += "<p>Thank you<br>Gopinath</p>";
//            System.out.println("print 14 " + LocalTime.now());
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            System.out.println("print 15 " + LocalTime.now());
//            helper.setFrom(fromAddress, senderName);
//            helper.setTo(toAddress);
//            helper.setSubject(subject);
//            helper.setText(mailContent, true);
//            System.out.println("print 16 " + LocalTime.now());
//            javaMailSender.send(message);
//
////            new Thread(() -> {
////                javaMailSender.send(message);
////            }).start();
//
//            System.out.println("print 17 " + LocalTime.now());
//            System.out.println("Email sent successfully to: " + toAddress);
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public ApiResponse verifyDetails(String email, String code) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<UserSignup> optionalUserSignup = userSignupRepo.findById(email);
//        System.out.println(optionalUserSignup);
        UserSignup userSignup = optionalUserSignup.get();
//        System.out.println(userSignup.getVerificationCode() + " &&& " + code);
//        System.out.println("User signup" + userSignup);

        if (userSignup.getVerificationCode().equals(code)) {
            apiResponse.setData(userSignup);
//            System.out.println(apiResponse.getData() + " API RESPONSe");
            return apiResponse;
        }
        apiResponse.setData("Failure");
        return apiResponse;
    }
}
