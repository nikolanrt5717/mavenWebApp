package com.msg.laza.project.dao.util;

import com.msg.laza.project.model.BankUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankUserRowMapper implements RowMapper<BankUser> {
    @Override
    public BankUser mapRow(ResultSet rs, int i) throws SQLException {
        return new BankUser(rs.getInt("USER_ID"),
                rs.getString("NAME"), rs.getString("SURNAME"),rs.getString("CITY"),
                rs.getString("BORN"));
    }
}
