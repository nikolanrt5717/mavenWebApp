package com.msg.laza.project.service;


import com.msg.laza.project.dao.BankUserDAO;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.BankUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BankUserService {
    @Autowired
    @Qualifier("bankUserDAO")
    private BankUserDAO bankUserDAO;

    public ArrayList<BankUser> getAllUsers(){
        return bankUserDAO.getAllUsers();
    }

    public BankUser getUserById(String id) throws CustomException.InvalidIdException {
        return bankUserDAO.getUserById(id);
    }

    public void addUser(String name, String surname, String city, String born) throws CustomException.InvalidNumberOfArgumentsException {
        bankUserDAO.addUser(name,surname,city,born);
    }

    public void deleteUser(String id) throws CustomException.InvalidIdException {
        bankUserDAO.deleteUser(id);
    }

    public void updateUser(String id,String surname,String city) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        bankUserDAO.updateUser(id,surname,city);
    }

    public BankUser getLastInsertedUser(){
        return bankUserDAO.getLastInsertedUser();
    }
}
