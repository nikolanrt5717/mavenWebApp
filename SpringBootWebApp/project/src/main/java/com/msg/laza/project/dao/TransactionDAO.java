package com.msg.laza.project.dao;

import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Transaction;

import java.util.List;

public interface TransactionDAO {
    public List<Transaction> getTransactionsById(String id, String senderAccountId, String receiverAccountId);
    public void addTransaction(String senderAccountId, String receiverAccountId, String funds) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException;
}
