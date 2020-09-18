package com.msg.laza.project.service;


import com.msg.laza.project.dao.AccountDAO;
import com.msg.laza.project.dao.sql.AccountDAOSql;
import com.msg.laza.project.model.Account;
import com.msg.laza.project.exception.CustomException;

import java.util.ArrayList;

public class AccountService {
    private static AccountService accountService = new AccountService();
    AccountDAO accountDAO;

    private AccountService() {
        this.accountDAO = AccountDAOSql.getAccountDaoSql();
    }

    public static AccountService getAccountService() {
        return accountService;
    }

    public ArrayList<Account> getAllAccounts(String id){
       return accountDAO.getAllAccounts(id);
    }

    public Account getAccountById(String id){
        try {
            return accountDAO.getAccountById(id);
        } catch (CustomException.InvalidIdException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAccount(String accountId, String userId, String funds){
        try {
            accountDAO.addAccount(accountId,userId,funds);
        } catch (CustomException.InvalidNumberOfArgumentsException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String id){
        try {
            accountDAO.deleteAccount(id);
        } catch (CustomException.InvalidIdException e) {
            e.printStackTrace();
        }
    }

    public Account getLastInsertedAccount(){
        return accountDAO.getLastInsertedAccount();
    }


}
