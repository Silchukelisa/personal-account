package com.example.preproj.service;

import com.example.preproj.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);


    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private EmailService emailService;


    public void sendSimpleEmail(String toAddress, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setFrom("silchuk.liza14@gmail.com");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public void mailAdmin(int id) {
        User user = userService.show(id);
        String message = user.getName() + " " + user.getLastName() + " wants to become an admin";
        List<User> allUsers = userService.allUsers();
        for (User u : allUsers) {
            if (u.getRoles().toString().contains("ROLE_ADMIN")) {
                emailService.sendSimpleEmail(u.getEmail(), "Hello", message);
            }
        }
    }

    public void acceptedApplication(int id) {
        String message = "Your application is accepted!";
        mail(id, message);
    }

    public void rejectedApplication(int id) {
        String message = "Your application has been rejected";
        mail(id, message);
    }

    public void mail(int id, String message) {
        User user = userService.show(id);
        try {
            emailService.sendSimpleEmail(user.getEmail(), "Hello", message);

        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", (Object) mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
        }
    }
}
