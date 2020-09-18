package com.msg.laza.project.service;

import com.msg.laza.project.dao.AccountDAO;
import com.msg.laza.project.dao.TransactionDAO;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    @Qualifier("transactionDAO")
    TransactionDAO transactionDAO;

    @Autowired
    @Qualifier("accountDAO")
    AccountDAO accountDAO;

    public List<Transaction> getTransactionsById(String id, String senderAccountId, String receiverAccountId){
        return transactionDAO.getTransactionsById(id, senderAccountId, receiverAccountId);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addTransaction(String senderAccountId, String receiverAccountId, String funds) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        accountDAO.subtractFunds(senderAccountId,funds);
        accountDAO.addFunds(receiverAccountId,funds);
        transactionDAO.addTransaction(senderAccountId, receiverAccountId, funds);
    }
}
