package com.msg.laza.project.dao.sql;

import com.msg.laza.project.dao.AccountDAO;
import com.msg.laza.project.dao.BankUserDAO;
import com.msg.laza.project.dao.util.AccountRowMapper;
import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Repository("accountDAO")
//@ConditionalOnProperty(name = "dao.type",havingValue = "Sql")
public class AccountDAOSql implements AccountDAO {
    @Autowired
    private BankUserDAO bankUserDAO;

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public ArrayList<Account> getAllAccounts(String id) throws CustomException.InvalidIdException {

        ArrayList<Account> accounts = new ArrayList<>();
        if(bankUserDAO.checkIfUserExists(id)) {
            jdbc.query(
                    "SELECT * FROM Account WHERE USER_ID = ?", new Object[]{id}, new AccountRowMapper()
            ).forEach(account -> accounts.add(account));
        }
        return accounts;
    }

    @Override
    public Account getAccountById(String id) throws CustomException.InvalidIdException {
        if(checkIfAccountExists(id)){
            return jdbc.queryForObject(
                    "SELECT * FROM Account WHERE  ACCOUNT_ID = ?",new Object[]{id} ,new AccountRowMapper());
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public boolean checkIfAccountExists(String id) throws CustomException.InvalidIdException {
        Account a = null;
        if(id!=null){
            a=jdbc.queryForObject(
                    "SELECT * FROM Account WHERE  ACCOUNT_ID = ?",new Object[]{id} ,new AccountRowMapper());
            if(a!=null)
                return true;
            else
                return false;
        }throw new CustomException.InvalidIdException(id);
    }

    @Override
    public void addAccount(int accountId, int userId, double funds) throws CustomException.InvalidNumberOfArgumentsException {
        if(funds<1)
            funds=0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String createdAt = dtf.format(now);
        if(accountId != 0 && userId != 0){
            jdbc.update("INSERT INTO Account(ACCOUNT_ID,USER_ID,CREATED_AT,FUNDS)" +
                    "VALUES (?,?,?,?)",accountId,userId,createdAt,funds);
        }else
            throw new CustomException.InvalidNumberOfArgumentsException();
    }

    public void addFunds(String id,String funds){
        jdbc.update("UPDATE ACCOUNT SET FUNDS = FUNDS + ? WHERE ACCOUNT_ID = ?",funds,id);
    }

    public void subtractFunds(String id,String funds){
        jdbc.update("UPDATE ACCOUNT SET FUNDS = FUNDS-? WHERE ACCOUNT_ID = ?",funds,id);
    }

    @Override
    public void deleteAccount(String id) throws CustomException.InvalidIdException {
        if(id!= null && checkIfAccountExists(id)){
            jdbc.update("DELETE FROM BankUser WHERE USER_ID = ?",id);
        }else
            throw new CustomException.InvalidIdException(id);
    }

    @Override
    public Account getLastInsertedAccount(){
        return jdbc.queryForObject(
                "SELECT * FROM Account WHERE ACCOUNT_ID = (SELECT MAX(ACCOUNT_ID) FROM Account)",new AccountRowMapper());
    }
}
