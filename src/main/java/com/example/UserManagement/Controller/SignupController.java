package com.example.UserManagement.Controller;

import com.example.UserManagement.DTO.LoginRequestDTO;
import com.example.UserManagement.DTO.SignUpRequestDTO;
import com.example.UserManagement.Model.UserSignup;
import com.example.UserManagement.Repository.UserSignupRepo;
import com.example.UserManagement.Service.SignupService;
import com.example.UserManagement.Util.JwtUtils;
import com.example.UserManagement.common.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserSignupRepo userSignupRepo;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> addUser(@RequestBody SignUpRequestDTO signUpRequestDTO, HttpServletRequest request) throws MessagingException, InvocationTargetException {

        String password = signUpRequestDTO.getPassword();
        String saltedPassword = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password, saltedPassword);
        signUpRequestDTO.setPassword(hashedPassword);
        String siteURL = getSiteURL(request);

        ApiResponse apiResponse = signupService.addUser(signUpRequestDTO, siteURL);
        return ResponseEntity
                .status(apiResponse.getStatus())
                .body(apiResponse);
    }

    @GetMapping("/userExists/{email}")
    public boolean checkUser(@PathVariable String email) {

        return signupService.checkUser(email);
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> checkData(@RequestBody LoginRequestDTO loginRequestDTO) {

        ApiResponse apiResponse = signupService.loginValidate(loginRequestDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/private")
    public ResponseEntity<ApiResponse> privateApi(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData("Private response");
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam(value = "email") String email, @RequestParam(value = "code") String code) {

        ApiResponse apiResponse = signupService.verifyDetails(email, code);
        Optional<UserSignup> optionalUserSignup = userSignupRepo.findById(email);
        UserSignup userSignup = optionalUserSignup.get();

        String htmlContent = "<html><head><title>Success Page</title></head><body style=\"text-align: center\" >" +
                "<h1>Success</h1><p>Your details have been verified successfully.</p>" +
                "<a href=\"http://127.0.0.1:5500/src/main/resources/templates/login.html\">login here </a>" +
                "</body></html>";

        if (apiResponse.getData().equals("Failure")) {

            htmlContent = "<html><head><title>Failure Page</title></head><body style=\"text-align: center\" > " +
                    "                    <h1>Failure</h1><p>Verification failed. Please check your details and try again.</p> " +
                    "<a href=\"http://127.0.0.1:5500/src/main/resources/templates/signup.html\">Signup here </a>" +
                    "                    </body></html>";

        } else if (userSignup.isVerificationStatus()) {

            htmlContent = "<html><head><title>Success Page</title></head><body style=\"text-align: center\" >" +
                    "<h1>Success</h1><p>Your details have been already verified </p>" +
                    "<a href=\"http://127.0.0.1:5500/src/main/resources/templates/login.html\">login here </a>" +
                    "</body></html>";

        } else if (!userSignup.isVerificationStatus() && !apiResponse.getData().equals("Failure")) {

            htmlContent = "<html><head><title>Success Page</title></head><body style=\"text-align: center\" >" +
                    "<h1>Success</h1><p>Your details have been verified successfully.</p>" +
                    "<a href=\"http://127.0.0.1:5500/src/main/resources/templates/login.html\">login here </a>" +
                    "</body></html>";

            userSignup.setVerificationStatus(true);
            userSignupRepo.save(userSignup);
        }

        return ResponseEntity.ok(htmlContent);
    }


    public String getSiteURL(HttpServletRequest request) {

        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
