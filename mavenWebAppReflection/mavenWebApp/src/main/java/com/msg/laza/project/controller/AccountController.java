package com.msg.laza.project.controller;

import com.msg.laza.project.controller.annotation.Controller;
import com.msg.laza.project.controller.annotation.Path;
import com.msg.laza.project.model.Account;
import com.msg.laza.project.service.AccountService;
import com.msg.laza.project.parsers.ParserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
public class AccountController{
    private ParserController parser;
    private AccountService accountService;

    public AccountController() {
        this.parser = new ParserController();
        this.accountService = AccountService.getAccountService();
    }


    @Path(value = "/api/accounts", method = "GET")
    public void getAccounts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("USER_ID");
        String accountId = req.getParameter("ACCOUNT_ID");
        PrintWriter out = resp.getWriter();
        parser.setParser(req);
        if(userId!=null){
            ArrayList<Account> accounts = accountService.getAllAccounts(userId);
            out.println(parser.getParsedList(accounts));
        }
        if(accountId!=null){
            Account a = accountService.getAccountById(accountId);
            out.println(parser.getParsedObject(a));
        }
    }

    @Path(value = "/api/accounts", method = "POST")
    public void createAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        parser.setParser(req);
        accountService.addAccount(req.getParameter("ACCOUNT_ID"),req.getParameter("USER_ID"),
                req.getParameter("FUNDS"));
        out.println(parser.getParsedObject(accountService.getLastInsertedAccount()));
    }

    @Path(value = "/api/accounts", method = "DELETE")
    public void deleteAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        accountService.deleteAccount(req.getParameter("ACCOUNT_ID"));
    }


}
