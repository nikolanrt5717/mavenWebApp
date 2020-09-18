package com.msg.laza.project.dao.sql;

import com.msg.laza.project.dao.AccountDAO;
import com.msg.laza.project.dao.BankUserDao;
import com.msg.laza.project.dao.sql.util.Database;
import com.msg.laza.project.model.Account;
import com.msg.laza.project.exception.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccountDAOSql implements AccountDAO {
    private static AccountDAOSql accountDaoSql = new AccountDAOSql();

    private BankUserDao bankUserDao = BankUserDAOSql.getBankUserDAOSql();

    private AccountDAOSql(){}


    public static AccountDAOSql getAccountDaoSql() {
        return accountDaoSql;
    }



    @Override
    public ArrayList<Account> getAllAccounts(String id){
        ArrayList<Account> accounts = new ArrayList<>();
        Connection conn = Database.getDb().getConn();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM Account WHERE USER_ID = ?");
            statement.setInt(1,Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account a = new Account(resultSet.getInt("Account_ID"),
                        resultSet.getInt("USER_ID"),
                        resultSet.getString("CREATED_AT"),
                        resultSet.getDouble("FUNDS"));
                accounts.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Database.getDb().releaseConn(conn);
        }
        return accounts;
    }

    @Override
    public Account getAccountById(String id) throws CustomException.InvalidIdException {
        Account a = null;
        if(checkIfAccountExists(id)){
            Connection conn = Database.getDb().getConn();
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM Account WHERE  ACCOUNT_ID = ?");
                statement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    a = new Account(resultSet.getInt("Account_ID"),
                            resultSet.getInt("USER_ID"),
                            resultSet.getString("CREATED_AT"),
                            resultSet.getDouble("FUNDS"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                Database.getDb().releaseConn(conn);
            }
        }else
            throw new CustomException.InvalidIdException(id);

        return a;
    }

    @Override
    public boolean checkIfAccountExists(String id) throws CustomException.InvalidIdException {
        if(id!=null){
            Connection conn = Database.getDb().getConn();
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM Account WHERE  ACCOUNT_ID = ?");
                statement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                Database.getDb().releaseConn(conn);
                Database.getDb().closeConn(conn);
            }
        }throw new CustomException.InvalidIdException(id);
    }

    @Override
    public void addAccount(String accountId, String userId, String funds) throws CustomException.InvalidNumberOfArgumentsException {
        if(funds==null)
            funds="0";
        Connection conn = Database.getDb().getConn();
        try {
            if(accountId !=null && userId != null && bankUserDao.checkIfUserExists(userId)){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String createdAt = dtf.format(now);
                try {
                    PreparedStatement statement = conn.prepareStatement("INSERT INTO Account(ACCOUNT_ID,USER_ID,CREATED_AT,FUNDS)" +
                            "VALUES (?,?,?,?)");
                    statement.setInt(1,Integer.parseInt(accountId));
                    statement.setInt(2,Integer.parseInt(userId));
                    statement.setString(3,createdAt);
                    statement.setDouble(4,Double.parseDouble(funds));
                    statement.executeUpdate();
                }  catch (SQLException e) {
                    e.printStackTrace();
                }
            }else
                throw new CustomException.InvalidNumberOfArgumentsException();
        } catch (CustomException.InvalidIdException e) {
            e.printStackTrace();
        }finally {
            Database.getDb().releaseConn(conn);
        }
    }

    @Override
    public void deleteAccount(String id) throws CustomException.InvalidIdException {
        if(id!= null && checkIfAccountExists(id)){
            Connection conn = Database.getDb().getConn();
            String sql = "";
            try {
                PreparedStatement statement = conn.prepareStatement(
                        "DELETE FROM Account WHERE Account_ID = ?");
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
    public Account getLastInsertedAccount(){
        Account a = null;
        Connection conn = Database.getDb().getConn();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM Account WHERE ACCOUNT_ID = (SELECT MAX(ACCOUNT_ID) FROM Account)");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                a = new Account(resultSet.getInt("ACCOUNT_ID"),
                        resultSet.getInt("USER_ID"),
                        resultSet.getString("CREATED_AT"),
                        resultSet.getDouble("FUNDS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Database.getDb().releaseConn(conn);
        }
        return a;
    }
}
