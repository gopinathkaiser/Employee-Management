package com.example.UserManagement.Service;

import com.example.UserManagement.Model.UserSignup;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendVerificationCode(UserSignup userSignup, String siteURL) {
        try {
            String toAddress = userSignup.getEmail();
            String fromAddress = "gopinathkaiser@gmail.com";
            String senderName = "Gopinath";
            String subject = "Please verify your registration";

            String mailContent = "<p>Dear " + userSignup.getName() + ",</p>";
            mailContent += "<p>Please click below link for verification:</p>";

            String verifyEmail = siteURL + "/Auth/verify?email=" + userSignup.getEmail() + "&code=" + userSignup.getVerificationCode();
            mailContent += "<h3><a href=\"" + verifyEmail + "\">VERIFY</a></h3>";
            mailContent += "<p>Thank you<br>Gopinath</p>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
