package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getTransferAmountById(int transferId) {
        BigDecimal transferAmount = null;
        String sql = "SELECT transfer_amount FROM transfer WHERE transfer_id = ?;";
        transferAmount = jdbcTemplate.queryForObject(sql, BigDecimal.class, transferId);
        return transferAmount;


    }

    @Override
    public int getFromAccountById(int transferId) {
        int fromAccountId;
        String sql = "SELECT account_from FROM transfer WHERE transfer_id = ?;";
        fromAccountId = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
        return fromAccountId;
    }


    @Override
    public int getToAccountById(int transferId) {
        int toAccountId;
        String sql = "SELECT account_to FROM transfer WHERE transfer_id = ?;";
        toAccountId = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
        return toAccountId;
    }

    @Override
    public String getTransferTypeById(int transferId) {
        String transferType;
        String sql = "SELECT transfer_type_desc FROM transfer_type " +
                "JOIN transfer ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "WHERE transfer_id = ?;";
        transferType = jdbcTemplate.queryForObject(sql, String.class, transferId);
        return transferType;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        //if (transfer.getAccountFrom() == transfer.getAccountTo() || .compareTo(transferAmount) < 0 || transferAmount.compareTo(BigDecimal.ZERO) <= 0
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "VALUES (?,?,?,?,?) RETURNING transfer_id;";
        Integer newTransferId;
        newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        if (newTransferId == null){
            return null;
        }

        else {
            Transfer newTransfer = new Transfer(newTransferId);
            return newTransfer;
        }
    }



}
