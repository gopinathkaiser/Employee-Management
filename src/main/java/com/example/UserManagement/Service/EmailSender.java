package com.example.UserManagement.Service;

import com.example.UserManagement.Model.UserSignup;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class EmailSender {


    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendVerificationCode(UserSignup userSignup, String siteURL) {
        try {
            System.out.println("print 11 " + LocalTime.now());
            String toAddress = userSignup.getEmail();
            String fromAddress = "gopinathkaiser@gmail.com";
            String senderName = "Gopinath";
            String subject = "Please verify your registration";
            String mailContent = "<p>Dear " + userSignup.getName() + ",</p>";
            mailContent += "<p>Please click below link for verification:</p>";
            System.out.println("print 12 " + LocalTime.now());
            String verifyEmail = siteURL + "/Auth/verify?email=" + userSignup.getEmail() + "&code=" + userSignup.getVerificationCode();
            System.out.println("print 13 " + LocalTime.now());
            mailContent += "<h3><a href=\"" + verifyEmail + "\">VERIFY</a></h3>";
            mailContent += "<p>Thank you<br>Gopinath</p>";
            System.out.println("print 14 " + LocalTime.now());
            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessage message = javaMailSender
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            System.out.println("print 15 " + LocalTime.now());
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            System.out.println("print 16 " + LocalTime.now());
            javaMailSender.send(message);

//            new Thread(() -> {
//                javaMailSender.send(message);
//            }).start();

            System.out.println("print 17 " + LocalTime.now());
            System.out.println("Email sent successfully to: " + toAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
