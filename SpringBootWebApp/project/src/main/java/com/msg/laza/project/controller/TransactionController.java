package com.msg.laza.project.controller;


import com.msg.laza.project.exception.CustomException;
import com.msg.laza.project.model.Transaction;
import com.msg.laza.project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/transactions")
    public List<Transaction> getTransactions(@RequestParam(required = false) String id, @RequestParam(required = false) String senderAccountId, @RequestParam(required = false) String receiverAccountId){
        return transactionService.getTransactionsById(id,senderAccountId,receiverAccountId);
    }

    @PostMapping("/api/transactions")
    public void createTransaction(@RequestParam String senderAccountId, @RequestParam String receiverAccountId, @RequestParam String funds)
            throws CustomException.InvalidNumberOfArgumentsException, CustomException.InvalidIdException {
        transactionService.addTransaction(senderAccountId,receiverAccountId,funds);
    }
}
