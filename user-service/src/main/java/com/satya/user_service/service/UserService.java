package com.satya.user_service.service;

import com.satya.user_service.dao.UserDao;
import com.satya.user_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${email.service.url}")
    private String emailServiceUrl;

    public ResponseEntity<Optional<User>> getUserById(Integer userId){
        try{
            return new ResponseEntity<>(userDao.findById(userId), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<User>> getAllUser(){
        try{
            return new ResponseEntity<>(userDao.findAll(),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> emailUser(Integer userId){
        ResponseEntity<Optional<User>> userDetails = getUserById(userId);
        String url = emailServiceUrl + "/mail/send";

        Map<String, String> payload = new HashMap<>();
        payload.put("to", "nksatyamdu007@gmail.com");
        payload.put("subject", "MailService - User Record");
        payload.put("body", userDetails.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
        return restTemplate.postForEntity(emailServiceUrl, request, String.class);

    }

    public ResponseEntity<String> getPasswordByEmail(String emailId){
        try{
            return new ResponseEntity<>(userDao.findUserByEmailId(emailId),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("Internal error occoured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> createNewUser(User user){
        try {
            userDao.save(user);
            return new ResponseEntity<>("User Created",HttpStatus.CREATED);
        }
        catch(org.springframework.dao.DataIntegrityViolationException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Unknown Error Occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
