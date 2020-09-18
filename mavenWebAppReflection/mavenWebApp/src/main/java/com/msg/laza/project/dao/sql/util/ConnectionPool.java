package com.msg.laza.project.dao.sql.util;

import java.sql.Connection;

public interface ConnectionPool {
    public Connection getConnection();
    public void releaseConnection(Connection connection);
}
