package com.msg.laza.project.controller;

import com.msg.laza.project.controller.annotation.Controller;
import com.msg.laza.project.controller.annotation.Path;
import com.msg.laza.project.model.Transaction;
import com.msg.laza.project.parsers.ParserController;
import com.msg.laza.project.service.AccountService;
import com.msg.laza.project.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {
    ParserController parser;
    TransactionService transactionService;

    public TransactionController() {
        this.parser = new ParserController();
        this.transactionService = TransactionService.getTransactionService();
    }


    @Path(value = "/api/transactions", method = "GET")
    public void getTransactions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("ID");
        String senderAccountId = req.getParameter("SENDER_ACCOUNT_ID");
        String receiverAccountId = req.getParameter("RECEIVER_ACCOUNT_ID");
        PrintWriter out = resp.getWriter();
        parser.setParser(req);
        List<Transaction> transactions = transactionService.getTransactionsById(id,senderAccountId,receiverAccountId);
        out.println(parser.getParsedList(transactions));
    }

    @Path(value = "/api/transactions", method = "POST")
    public void createTransaction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        transactionService.addTransaction(req.getParameter("SENDER_ACCOUNT_ID"),
                req.getParameter("RECEIVER_ACCOUNT_ID"),req.getParameter("FUNDS"));
    }
}
