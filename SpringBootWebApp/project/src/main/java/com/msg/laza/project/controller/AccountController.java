package com.msg.laza.project.controller;

import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Account;
import com.msg.laza.project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/api/accounts")
    public List<Account> getAccounts(@RequestParam(required = false) String userId, @RequestParam(required = false) String accountId) throws CustomException.InvalidIdException {
        List<Account>accounts = new ArrayList<>();
        if(userId!=null){
            accounts.addAll(accountService.getAllAccounts(userId));
        }
        if(accountId!=null){
            accounts.add(accountService.getAccountById(accountId));
        }
        return accounts;
    }

    @PostMapping("/api/accounts")
    public void createAccount(@RequestBody Account a) throws CustomException.InvalidNumberOfArgumentsException {
        accountService.addAccount(a.getId(),a.getUserId(),a.getfunds());
    }

    @DeleteMapping("/api/accounts")
    public void deleteAccount(@RequestParam String id) throws CustomException.InvalidIdException {
        accountService.deleteAccount(id);
    }
}
