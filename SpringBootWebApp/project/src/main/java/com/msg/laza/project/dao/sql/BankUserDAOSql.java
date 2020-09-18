package com.msg.laza.project.dao.sql;

import com.msg.laza.project.dao.BankUserDAO;
import com.msg.laza.project.dao.util.BankUserRowMapper;
import com.msg.laza.project.model.BankUser;
import com.msg.laza.project.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository("bankUserDAO")
public class BankUserDAOSql implements BankUserDAO {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public ArrayList<BankUser> getAllUsers(){

        ArrayList<BankUser> users = new ArrayList<>();
        jdbc.query(
                "SELECT * FROM BANKUSER", new BankUserRowMapper()
        ).forEach(bankUser -> users.add(bankUser));

        return users;
    }

    @Override
    public BankUser getUserById(String id) throws CustomException.InvalidIdException {

        if(checkIfUserExists(id)){
            return jdbc.queryForObject(
                    "SELECT * FROM BANKUSER WHERE USER_ID = ?",new Object[]{id} ,new BankUserRowMapper());
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public boolean checkIfUserExists(String id) throws CustomException.InvalidIdException {
        BankUser u = null;
        if(id!=null){
           u=jdbc.queryForObject(
                    "SELECT * FROM BANKUSER WHERE USER_ID = ?",new Object[]{id} ,new BankUserRowMapper()
            );

           if(u!=null)
               return true;
           else
               return false;
        }throw new CustomException.InvalidIdException(id);
    }

    @Override
    public void addUser(String name, String surname, String city, String born) throws CustomException.InvalidNumberOfArgumentsException {

        if(name != null && surname != null && city !=null && born!= null){
            jdbc.update("INSERT INTO BankUser(NAME,SURNAME,CITY,BORN)" +
                    "VALUES (?,?,?,?)",name,surname,city,born);
        }else
            throw new CustomException.InvalidNumberOfArgumentsException();
    }

    @Override
    public void deleteUser(String id) throws CustomException.InvalidIdException {
        if(id!= null && checkIfUserExists(id)){
            jdbc.update("DELETE FROM BankUser WHERE USER_ID = ?",id);
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public void updateUser(String id,String surname,String city) throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        if (id != null  && checkIfUserExists(id)){
            if(surname!=null || city!=null) {
                if (surname != null && city != null) {
                    jdbc.update("UPDATE BankUser SET SURNAME =?,CITY = ? WHERE USER_ID =?",surname,city,id);
                } else if (surname != null && city == null) {
                    jdbc.update("UPDATE BankUser SET SURNAME = ? WHERE USER_ID = ?",surname,id);
                } else if (surname == null && city != null) {
                    jdbc.update("UPDATE BankUser SET CITY = ? WHERE USER_ID = ?",city,id);
                }
            }else
                throw new CustomException.InvalidNumberOfArgumentsException();
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public BankUser getLastInsertedUser(){
        return jdbc.queryForObject(
                "SELECT * FROM BankUser WHERE USER_ID = (SELECT MAX(USER_ID) FROM BankUser)", new BankUserRowMapper());
    }
}
