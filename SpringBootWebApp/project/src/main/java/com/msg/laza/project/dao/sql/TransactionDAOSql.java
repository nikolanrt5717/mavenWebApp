package com.msg.laza.project.dao.sql;

import com.msg.laza.project.dao.AccountDAO;
import com.msg.laza.project.dao.TransactionDAO;
import com.msg.laza.project.dao.util.TransactionRowMapper;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository("transactionDAO")
public class TransactionDAOSql implements TransactionDAO {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public List<Transaction> getTransactionsById(String id, String senderAccountId, String receiverAccountId) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        if (id != null) {
            jdbc.query(
                    "SELECT * FROM Transactions  WHERE  ID = ?", new Object[]{id}, new TransactionRowMapper()
            ).forEach(transaction -> transactions.add(transaction));
        } else if (senderAccountId != null) {
            jdbc.query(
                    "SELECT * FROM Transactions  WHERE  SENDER_ACCOUNT_ID = ?", new Object[]{senderAccountId}, new TransactionRowMapper()
            ).forEach(transaction -> transactions.add(transaction));
        } else if (receiverAccountId != null) {
            jdbc.query(
                    "SELECT * FROM Transactions  WHERE  RECEIVER_ACCOUNT_ID = ?", new Object[]{receiverAccountId}, new TransactionRowMapper()
            ).forEach(transaction -> transactions.add(transaction));
        }
        return transactions;
    }

    @Override
    public void addTransaction(String senderAccountId, String receiverAccountId, String funds) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {

        if (senderAccountId != null && receiverAccountId != null && funds != null) {
            if (accountDAO.checkIfAccountExists(senderAccountId) && accountDAO.checkIfAccountExists(receiverAccountId)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String transactionTimestamp = dtf.format(now);
                jdbc.update("INSERT INTO Transactions (SENDER_ACCOUNT_ID,RECEIVER_ACCOUNT_ID,TRANSCTION_TIMESTAMP,FUNDS)" +
                        "VALUES (?,?,?,?)",senderAccountId,receiverAccountId,transactionTimestamp,funds);
            }
        } else
            throw new CustomException.InvalidNumberOfArgumentsException();

    }

}
