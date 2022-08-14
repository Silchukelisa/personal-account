package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.service.email.EmailService;
import com.example.preproj.service.request.RequestService;
import com.example.preproj.service.user.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mails/")
public class EmailRestController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailRestController.class);

    @Autowired
    private RequestService requestService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserServiceImp userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> mail(@PathVariable("id") Integer id) {

        User user = userService.show(id);
        String message = user.getName() + " " + user.getLastName() + " wants to become an admin";
        List<User> allUsers = userService.allUsers();
        requestService.add(id);

        try {

            for (User u : allUsers) {
                if (u.getRoles().toString().contains("ROLE_ADMIN")) {
                    emailService.sendSimpleEmail(u.getEmail(), "Hello", message);
                }
            }
        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", (Object) mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Integer id) {
        User user = userService.show(id);
        String message = "Your application is accepted!";

        try {
            emailService.sendSimpleEmail(user.getEmail(), "Hello", message);

        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", (Object) mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        requestService.update(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        User user = userService.show(id);
        String message = "Your application has been rejected";

        try {
            emailService.sendSimpleEmail(user.getEmail(), "Hello", message);

        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", (Object) mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        requestService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

