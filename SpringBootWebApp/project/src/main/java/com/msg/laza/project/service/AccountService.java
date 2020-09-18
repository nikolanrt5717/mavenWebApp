package com.msg.laza.project.service;


import com.msg.laza.project.dao.AccountDAO;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService {

    @Autowired
    @Qualifier("accountDAO")
    private AccountDAO accountDAO;

    public ArrayList<Account> getAllAccounts(String id) throws CustomException.InvalidIdException {
       return accountDAO.getAllAccounts(id);
    }

    public Account getAccountById(String id) throws CustomException.InvalidIdException {
        return accountDAO.getAccountById(id);
    }

    public void addAccount(int accountId, int userId, double funds) throws CustomException.InvalidNumberOfArgumentsException {
        accountDAO.addAccount(accountId,userId,funds);
    }

    public void deleteAccount(String id) throws CustomException.InvalidIdException {
        accountDAO.deleteAccount(id);
    }

    public Account getLastInsertedAccount(){
        return accountDAO.getLastInsertedAccount();
    }


}
