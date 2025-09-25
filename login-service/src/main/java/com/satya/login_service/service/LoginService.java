package com.satya.login_service.service;


import com.satya.login_service.model.Login;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackUserInfo")
    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000))
    public String getPassFromUser(Login login){
            String url = userServiceUrl + "/user/get/" + login.getEmail();
            ResponseEntity<String> response = restTemplate.getForEntity(
                    url,
                    String.class);
            return response.getBody();

    }


    public ResponseEntity<String> login(Login login) throws RuntimeException{
        //http://localhost:8081/user/get/sample@gmail.com
        String currentPassword = getPassFromUser(login);
        System.out.println(currentPassword + "===================================================");
        if (login.getPassword().equals(currentPassword)) {
            return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        }
        else if (currentPassword.equals("503")){
            return new ResponseEntity<>("User service is not available!", HttpStatus.UNAUTHORIZED);
        }
        //throw new RuntimeException("Manually triggered error!");
        return new ResponseEntity<>("Login Failed!", HttpStatus.UNAUTHORIZED);
    }

    @Recover
    public String fallbackUserInfo(Login login, Throwable t) {
        return "503";
    }
}
