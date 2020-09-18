package com.msg.laza.project.dao.sql.util;

import com.msg.laza.project.property.PropertiesLoader;

import java.sql.*;

public class Database {
    private static Database db = new Database();
    private ConnectionPool pool;

    private Database() {
        pool= new SimpleConnectionPool(PropertiesLoader.getPropertiesLoader().getDatabasePoolSize(),PropertiesLoader.getPropertiesLoader().getDatabaseUrl());
    }

    public static Database getDb() {
        return db;
    }


    public Connection getConn() {
        return pool.getConnection();
    }

    public void releaseConn(Connection conn){
        pool.releaseConnection(conn);
    }

    public void closeConn(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
