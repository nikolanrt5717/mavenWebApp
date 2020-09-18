package com.msg.laza.project.dao.sql;

import com.msg.laza.project.dao.TransactionDAO;
import com.msg.laza.project.dao.sql.util.Database;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOSql implements TransactionDAO {
    private static TransactionDAOSql transactionDAOSql = new TransactionDAOSql();
    private AccountDAOSql accountDAOSql = AccountDAOSql.getAccountDaoSql();

    private TransactionDAOSql() {
    }

    public static TransactionDAOSql getTransactionDAOSql() {
        return transactionDAOSql;
    }

    @Override
    public List<Transaction> getTransactionsById(String id, String senderAccountId, String receiverAccountId) {
        Connection conn = Database.getDb().getConn();
        PreparedStatement statement = null;
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            if (id != null) {
                statement = conn.prepareStatement(
                        "SELECT * FROM Transactions  WHERE  ID = ?");
                statement.setInt(1, Integer.parseInt(id));
            } else if (senderAccountId != null) {
                statement = conn.prepareStatement(
                        "SELECT * FROM Transactions  WHERE  SENDER_ACCOUNT_ID = ?");
                statement.setInt(1, Integer.parseInt(senderAccountId));
            } else if (receiverAccountId != null) {
                statement = conn.prepareStatement(
                        "SELECT * FROM Transactions  WHERE  RECEIVER_ACCOUNT_ID = ?");
                statement.setInt(1, Integer.parseInt(receiverAccountId));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transaction t = new Transaction(resultSet.getInt("ID"),
                        resultSet.getInt("SENDER_ACCOUNT_ID"),
                        resultSet.getInt("RECEIVER_ACCOUNT_ID"),
                        resultSet.getString("TRANSCTION_TIMESTAMP"),
                        resultSet.getDouble("FUNDS"));
                transactions.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.getDb().releaseConn(conn);
        }
        return transactions;
    }

    @Override
    public void addTransaction(String senderAccountId, String receiverAccountId, String funds) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        Connection conn = Database.getDb().getConn();

        if (senderAccountId != null && receiverAccountId != null && funds != null) {
            if (accountDAOSql.checkIfAccountExists(senderAccountId) && accountDAOSql.checkIfAccountExists(receiverAccountId)) {
                try {
                    conn.setAutoCommit(false);
                    PreparedStatement statement = conn.prepareStatement("UPDATE ACCOUNT SET FUNDS = FUNDS-? WHERE ACCOUNT_ID = ?");
                    statement.setDouble(1, Double.parseDouble(funds));
                    statement.setInt(2, Integer.parseInt(senderAccountId));
                    statement.executeUpdate();

                    statement = conn.prepareStatement("UPDATE ACCOUNT SET FUNDS = FUNDS + ? WHERE ACCOUNT_ID = ?");
                    statement.setDouble(1, Double.parseDouble(funds));
                    statement.setInt(2, Integer.parseInt(receiverAccountId));
                    statement.executeUpdate();


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String transactionTimestamp = dtf.format(now);

                    statement = conn.prepareStatement("INSERT INTO Transactions (SENDER_ACCOUNT_ID,RECEIVER_ACCOUNT_ID,TRANSCTION_TIMESTAMP,FUNDS)" +
                            "VALUES (?,?,?,?)");
                    statement.setInt(1, Integer.parseInt(senderAccountId));
                    statement.setInt(2, Integer.parseInt(receiverAccountId));
                    statement.setString(3, transactionTimestamp);
                    statement.setDouble(4, Double.parseDouble(funds));
                    statement.executeUpdate();

                    conn.commit();
                } catch (Exception e) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                } finally {
                    try {
                        conn.setAutoCommit(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Database.getDb().releaseConn(conn);
                }
            }
        } else
            throw new CustomException.InvalidNumberOfArgumentsException();

    }

}
