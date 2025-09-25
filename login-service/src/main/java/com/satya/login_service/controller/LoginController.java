package com.satya.login_service.controller;


import com.satya.login_service.model.Login;
import com.satya.login_service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class LoginController {

    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login){
        try{
            return loginService.login(login);
        }
        catch(RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> check(){
        System.out.println("reached Here =======================================");
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
