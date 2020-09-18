package com.msg.laza.project.dao.util;

import com.msg.laza.project.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int i) throws SQLException {
        return new Account(
                rs.getInt("ACCOUNT_ID"),
                rs.getInt("USER_ID"),
                rs.getString("CREATED_AT"),
                rs.getDouble("FUNDS"));
    }
}
