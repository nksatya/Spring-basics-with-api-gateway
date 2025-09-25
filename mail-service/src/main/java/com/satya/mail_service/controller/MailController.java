package com.satya.mail_service.controller;

import com.satya.mail_service.model.EmailRequest;
import com.satya.mail_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailRequest emailRequest) {
        mailService.sendEmail(emailRequest);
        return ResponseEntity.ok("Email sent successfully");
    }
}
