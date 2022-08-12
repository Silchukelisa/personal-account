package com.example.preproj.controller;

import com.example.preproj.model.User;
import com.example.preproj.service.EmailService;
import com.example.preproj.service.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mail/")
public class EmailRestController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailRestController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserServiceImp userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> mail(@PathVariable("id") Integer id) {

        User user = userService.show(id);
        String message = user.getName() + " " + user.getLastName() + " wants to become an admin";
        List<User> allUsers = userService.allUsers();

        try {

            for (User u : allUsers) {
                if (u.getRoles().toString().contains("ROLE_ADMIN")) {
                    emailService.sendSimpleEmail(u.getEmail(), "Welcome", message);
                }
            }
        } catch (MailException mailException) {
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

}

