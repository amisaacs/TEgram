package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
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

    public TenmoService(String url) {
        this.baseUrl = url;
    }

    public BigDecimal getBalance(String authToken) {
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "/balance", HttpMethod.GET,
                    createCredentialsEntity(authToken), BigDecimal.class);
            balance = response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }

        return balance;
    }

    // list users without current user
    public User[] getUsers(String authToken) {
        User[] users = null;
        try {
//            HttpEntity<> entity = createCredentialsEntity(authToken);
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "/listUsers", HttpMethod.GET,
                    createCredentialsEntity(authToken), User[].class);
            users = response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    // create HTTP request to server to make transfer
    public Transfer makeTransfer(Transfer transfer, BigDecimal transferAmount, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        Transfer returnedTransfer = null;
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

        try {
            returnedTransfer = restTemplate.postForObject(baseUrl + "/transfer", entity,
                    Transfer.class);

        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }


    public Transfer[] listTransfersByUserId(long userId, String authToken) {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "/listTransfers", HttpMethod.GET,
                    createCredentialsEntity(authToken), Transfer[].class);
            transfers = response.getBody();

        } catch (ResourceAccessException | RestClientResponseException e) {

            BasicLogger.log(e.getMessage());
        }
        return transfers;

    }

    public Account getAccount(String username, String authToken) {
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/account/" + username, HttpMethod.GET,
                    createCredentialsEntity(authToken), Account.class);

            account = response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {

            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public User getUser(Long accountId, String authToken) {
        User user = null;
        try{
            ResponseEntity<User> response = restTemplate.exchange(baseUrl + "/user/" + accountId, HttpMethod.GET,
                    createCredentialsEntity(authToken), User.class);

            user = response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {

            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    public Transfer getTransfer(Long transferId, String authToken) {
        Transfer transfer = null;
        try{
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "/transfer/" + transferId, HttpMethod.GET,
                    createCredentialsEntity(authToken), Transfer.class);
            transfer = response.getBody();

        }catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }


    private HttpEntity createCredentialsEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
