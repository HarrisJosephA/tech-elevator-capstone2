package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
public class AccountController {
    private UserDao userDao;
    public AccountController(UserDao userDao) {
        this.userDao = userDao;
    }
// Made getBalance
    @RequestMapping(path = "account/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (@PathVariable int id){
        BigDecimal balance = userDao.getBalanceById(id);
        return balance;
    }







}
