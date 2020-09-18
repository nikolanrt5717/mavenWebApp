package com.msg.laza.project.dao;

import com.msg.laza.project.model.Account;
import com.msg.laza.project.exception.CustomException;

import java.util.ArrayList;

public interface AccountDAO {
    public ArrayList<Account> getAllAccounts(String id);
    public Account getAccountById(String id) throws CustomException.InvalidIdException;
    public boolean checkIfAccountExists(String id) throws CustomException.InvalidIdException;
    public void addAccount(String accountId, String userId, String funds) throws CustomException.InvalidNumberOfArgumentsException;
    public void deleteAccount(String id) throws CustomException.InvalidIdException;
    public Account getLastInsertedAccount();
}
