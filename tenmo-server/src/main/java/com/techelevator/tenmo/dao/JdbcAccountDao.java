package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account updateAccount(Account account){

        String sql = "UPDATE account SET user_id = ?, balance = ?;";
        jdbcTemplate.update(sql, account.getUserId(), account.getBalance());

        return account;
    }





}
