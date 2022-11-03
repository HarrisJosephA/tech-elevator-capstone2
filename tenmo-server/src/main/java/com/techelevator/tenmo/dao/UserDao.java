package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {


    List<User> findAll();

    BigDecimal getBalanceById (int userId);

    User getUserById(int id);

    User findByUsername(String username);

    int getIdByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}
