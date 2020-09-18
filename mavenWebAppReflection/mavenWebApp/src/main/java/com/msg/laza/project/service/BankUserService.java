package com.msg.laza.project.service;


import com.msg.laza.project.dao.sql.BankUserDAOSql;
import com.msg.laza.project.dao.BankUserDao;
import com.msg.laza.project.model.BankUser;
import com.msg.laza.project.exception.CustomException;

import java.util.ArrayList;

public class BankUserService {
    private static BankUserService bankUserService = new BankUserService();
    BankUserDao bankUserDAO;

    private BankUserService() {
        this.bankUserDAO = BankUserDAOSql.getBankUserDAOSql();
    }

    public static BankUserService getbankUserService() {
        return bankUserService;
    }

    public ArrayList<BankUser> getAllUsers(){
       return bankUserDAO.getAllUsers();
    }

    public BankUser getUserById(String id){
        try {
            return bankUserDAO.getUserById(id);
        } catch (CustomException.InvalidIdException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(String name, String surname, String city, String born){
        try {
            bankUserDAO.addUser(name,surname,city,born);
        } catch (CustomException.InvalidNumberOfArgumentsException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String id){
        try {
            bankUserDAO.deleteUser(id);
        } catch (CustomException.InvalidIdException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String id,String surname,String city){
        try {
            bankUserDAO.updateUser(id,surname,city);
        } catch (CustomException.InvalidNumberOfArgumentsException e) {
            e.printStackTrace();
        } catch (CustomException.InvalidIdException e) {
            e.printStackTrace();
        }
    }

    public BankUser getLastInsertedUser(){
         return bankUserDAO.getLastInsertedUser();
    }


}
