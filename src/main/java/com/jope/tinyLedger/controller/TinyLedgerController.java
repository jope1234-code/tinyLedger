package com.jope.tinyLedger.controller;

import com.jope.tinyLedger.service.TinyLedgerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TinyLedgerController {

    private final TinyLedgerService service;

    public TinyLedgerController(TinyLedgerService service){
        this.service = service;
    }

    @GetMapping("/balance")
    public String getLedgerBalance(){
        return service.getLedgerBalance().toString();
    }

    @GetMapping("/transactions")
    public List<String> getTransactionRecords(){
        return service.getTransactionRecords();
    }

    @PostMapping("/transactions/deposit")
    public void deposit(double amount){
        service.depositToLedger(amount);
    }

    @PostMapping("/transactions/withdraw")
    public void withdraw(double amount){
        service.withdrawFromLedger(amount);
    }

}
