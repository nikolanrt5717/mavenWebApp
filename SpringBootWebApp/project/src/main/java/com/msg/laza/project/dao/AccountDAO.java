package com.msg.laza.project.dao;

import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Account;

import java.util.ArrayList;

public interface AccountDAO {
    public ArrayList<Account> getAllAccounts(String id) throws CustomException.InvalidIdException;
    public Account getAccountById(String id) throws CustomException.InvalidIdException;
    public boolean checkIfAccountExists(String id) throws CustomException.InvalidIdException;
    public void addAccount(int accountId, int userId, double funds) throws CustomException.InvalidNumberOfArgumentsException;
    public void deleteAccount(String id) throws CustomException.InvalidIdException;
    public Account getLastInsertedAccount();
    public void subtractFunds(String id,String funds);
    public void addFunds(String id,String funds);

}
