package com.msg.laza.project.dao.sql.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SimpleConnectionPool implements ConnectionPool {
    private BlockingQueue<Connection> connectionPool;


    public SimpleConnectionPool(int poolSize,String url) {
        BlockingQueue<Connection> pool = new ArrayBlockingQueue(poolSize);

        for (int i = 0; i < poolSize; i++) {
            try {
                Class.forName("org.sqlite.JDBC");
                pool.add(DriverManager.getConnection(url));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.connectionPool = pool;
    }

    @Override
    public Connection getConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
    }


}
