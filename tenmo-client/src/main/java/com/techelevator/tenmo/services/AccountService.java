package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.web.client.RestTemplate;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;

public class AccountService {

    private static  final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     *  Getting account balance
     */

    public BigDecimal getBalance(int userId) {
        BigDecimal balance = null;

        try {
            balance = restTemplate.getForObject(API_BASE_URL + "account/" + userId+ "/balance", BigDecimal.class);
        } catch (RestClientResponseException |ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public User[] listUsers(){
        User[] users = restTemplate.getForObject(API_BASE_URL + "account", User[].class);
        return users;

    }

    public void transferTo(String transferName, BigDecimal transferAmount){

        try {
            restTemplate.put(API_BASE_URL + "account/" + transferName + "/transfer", BigDecimal.class);
        }
        catch (RestClientResponseException |ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
    }

    public int getIdByUsername(String username) {
        int accountId = 0;
        try {
            accountId = restTemplate.getForObject(API_BASE_URL + "tenmo_user/" + username, Integer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountId;
    }

}
