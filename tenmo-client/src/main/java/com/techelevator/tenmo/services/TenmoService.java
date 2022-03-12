package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class TenmoService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public TenmoService(String url){
        this.baseUrl = url;
    }

    public BigDecimal getBalance(String authToken){
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "/balance", HttpMethod.GET,
                    createCredentialsEntity(authToken), BigDecimal.class);
        balance = response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }

        return balance;
    }

    // list users without current user
    public User[] getUsers(String authToken){
        User[] users = null;
        try{
//            HttpEntity<> entity = createCredentialsEntity(authToken);
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "/listUsers", HttpMethod.GET,
                    createCredentialsEntity(authToken), User[].class );
            users = response.getBody();
        }catch (ResourceAccessException | RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }
        return users;
    }
    // choose a user to send money to
    // prompt for amount from sender
    // create transfer object
    // create HTTP request to server to make transfer



    private HttpEntity createCredentialsEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
