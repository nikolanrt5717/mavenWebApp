package com.msg.laza.project.dao.sql;

import com.msg.laza.project.dao.sql.util.Database;
import com.msg.laza.project.dao.BankUserDao;
import com.msg.laza.project.model.BankUser;
import com.msg.laza.project.exception.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class BankUserDAOSql implements BankUserDao {
    private static BankUserDAOSql bankUserDAO = new BankUserDAOSql();
    private BankUserDAOSql() {
    }

    public static BankUserDAOSql getBankUserDAOSql() {
        return bankUserDAO;
    }

    @Override
    public ArrayList<BankUser> getAllUsers(){
        Connection conn = Database.getDb().getConn();
        ArrayList<BankUser> users = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM BankUser");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BankUser u = new BankUser(resultSet.getInt("USER_ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("SURNAME"),
                        resultSet.getString("CITY"),
                        resultSet.getString("BORN"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Database.getDb().releaseConn(conn);
        }
        return users;
    }

    @Override
    public BankUser getUserById(String id) throws CustomException.InvalidIdException {
        BankUser u = null;
        if(checkIfUserExists(id)){
            Connection conn = Database.getDb().getConn();
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM BankUser WHERE  USER_ID = ?");
                statement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    u = new BankUser(resultSet.getInt("USER_ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("SURNAME"),
                            resultSet.getString("CITY"),
                            resultSet.getString("BORN"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                Database.getDb().releaseConn(conn);
            }

        }else
            throw new CustomException.InvalidIdException(id);

        return u;
    }

    @Override
    public boolean checkIfUserExists(String id) throws CustomException.InvalidIdException {

        if(id!=null){
            Connection conn = Database.getDb().getConn();
            System.out.println(conn.toString());
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM BankUser WHERE  USER_ID = ?");
                statement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                Database.getDb().releaseConn(conn);
            }
        }throw new CustomException.InvalidIdException(id);
    }

    @Override
    public void addUser(String name, String surname, String city, String born) throws CustomException.InvalidNumberOfArgumentsException {

        if(name != null && surname != null && city !=null && born!= null){
            Connection conn = Database.getDb().getConn();
            try {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO BankUser(NAME,SURNAME,CITY,BORN)" +
                        "VALUES (?,?,?,?)");
                statement.setString(1,name);
                statement.setString(2,surname);
                statement.setString(3,city);
                statement.setString(4,born);
                statement.executeUpdate();

            }  catch (SQLException e) {
                e.printStackTrace();
            }finally {
                Database.getDb().releaseConn(conn);
            }
        }else
            throw new CustomException.InvalidNumberOfArgumentsException();
    }

    @Override
    public void deleteUser(String id) throws CustomException.InvalidIdException {
        if(id!= null && checkIfUserExists(id)){
            Connection conn = Database.getDb().getConn();
            String sql = "";
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "DELETE FROM BankUser WHERE USER_ID = ?");
                statement.setInt(1, Integer.parseInt(id));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                Database.getDb().releaseConn(conn);
            }
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public void updateUser(String id,String surname,String city) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        if (id != null  && checkIfUserExists(id)){
            if(surname!=null || city!=null) {
                Connection conn = Database.getDb().getConn();
                PreparedStatement statement = null;
                try {
                    if (surname != null && city != null) {
                        statement = conn.prepareStatement(
                                "UPDATE BankUser SET SURNAME =?,CITY = ? WHERE USER_ID =?");
                        statement.setString(1, surname);
                        statement.setString(2, city);
                        statement.setInt(3, Integer.parseInt(id));
                    } else if (surname != null && city == null) {
                        statement = conn.prepareStatement(
                                "UPDATE BankUser SET SURNAME = ? WHERE USER_ID = ?");
                        statement.setString(1, surname);
                        statement.setInt(2, Integer.parseInt(id));
                    } else if (surname == null && city != null) {
                        statement = conn.prepareStatement(
                                "UPDATE BankUser SET CITY = ? WHERE USER_ID = ?");
                        statement.setString(1, city);
                        statement.setInt(2, Integer.parseInt(id));
                    }
                    statement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    Database.getDb().releaseConn(conn);
                }
            }else
                throw new CustomException.InvalidNumberOfArgumentsException();
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public BankUser getLastInsertedUser(){
        Connection conn = Database.getDb().getConn();
        BankUser u = null;
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM BankUser WHERE USER_ID = (SELECT MAX(USER_ID) FROM BankUser)");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                u = new BankUser(resultSet.getInt("USER_ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("SURNAME"),
                        resultSet.getString("CITY"),
                        resultSet.getString("BORN"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Database.getDb().releaseConn(conn);
        }
        return u;
    }
}
