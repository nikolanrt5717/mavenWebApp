package com.msg.laza.project.service;

import com.msg.laza.project.dao.TransactionDAO;
import com.msg.laza.project.dao.sql.TransactionDAOSql;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Transaction;

import java.util.List;

public class TransactionService {
    private static TransactionService transactionService = new TransactionService();
    TransactionDAO transactionDAO;

    private TransactionService() {
        this.transactionDAO = TransactionDAOSql.getTransactionDAOSql();
    }

    public static TransactionService getTransactionService() {
        return transactionService;
    }

    public List<Transaction> getTransactionsById(String id,String senderAccountId,String receiverAccountId){
        return transactionDAO.getTransactionsById(id, senderAccountId, receiverAccountId);
    }

    public void addTransaction(String senderAccountId, String receiverAccountId, String funds){
        try {
            transactionDAO.addTransaction(senderAccountId, receiverAccountId, funds);
        } catch (CustomException.InvalidNumberOfArgumentsException | CustomException.InvalidIdException e) {
            e.printStackTrace();
        }
    }
}
