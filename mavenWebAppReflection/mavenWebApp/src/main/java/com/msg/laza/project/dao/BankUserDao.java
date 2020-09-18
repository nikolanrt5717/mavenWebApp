package com.msg.laza.project.dao;

import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.BankUser;

import java.util.ArrayList;

public interface BankUserDao {
    public ArrayList<BankUser> getAllUsers();
    public BankUser getUserById(String id)throws CustomException.InvalidIdException;
    public boolean checkIfUserExists(String id) throws CustomException.InvalidIdException;
    public void addUser(String name, String surname, String city, String born) throws CustomException.InvalidNumberOfArgumentsException;
    public void deleteUser(String id) throws CustomException.InvalidIdException;
    public void updateUser(String id, String surname, String city) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException;
    public BankUser getLastInsertedUser();
}
