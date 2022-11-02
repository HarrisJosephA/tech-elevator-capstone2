package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

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

    @RequestMapping(path = "account", method = RequestMethod.GET)
    public List<User> userList(){
        return userDao.findAll();
    }

    //TransferTo
    @RequestMapping(path = "account/{id}/balance", method = RequestMethod.PUT)
    public void transferAmount (@RequestBody BigDecimal transferAmount, @PathVariable int fromId, int toId){
        userDao.transferTo(fromId, toId, transferAmount);
    }







}
