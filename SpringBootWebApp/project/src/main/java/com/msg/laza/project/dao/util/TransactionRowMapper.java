package com.msg.laza.project.dao.util;

import com.msg.laza.project.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int i) throws SQLException {
        return new Transaction(
                rs.getInt("ID"),
                rs.getInt("SENDER_ACCOUNT_ID"),
                rs.getInt("RECEIVER_ACCOUNT_ID"),
                rs.getString("TRANSCTION_TIMESTAMP"),
                rs.getDouble("FUNDS"));
    }
}
