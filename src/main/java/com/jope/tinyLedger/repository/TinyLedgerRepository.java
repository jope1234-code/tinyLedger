package com.jope.tinyLedger.repository;

import java.math.BigDecimal;
import java.util.List;

/**
  *   This is a bit of a hack for the sake of not having a wired up database or equivalent
  *   under the hood - instead treat these methods as distinct lookups/inserts etc on a typical
  *   JPARepository way of doing things
  */
public class TinyLedgerRepository {

    private BigDecimal ledgerBalance;
    private final List<String> transactionRecords;

    public TinyLedgerRepository(BigDecimal ledgerBalance, List<String> transactionRecords){
        this.ledgerBalance = ledgerBalance;
        this.transactionRecords = transactionRecords;
    }

    public BigDecimal getLedgerBalance(){
        return this.ledgerBalance;
    }

    public List<String> getTransactionRecords(){
        return this.transactionRecords;
    }

    public void updateLedger(BigDecimal amount, String transactionRecord){
        ledgerBalance = amount;
        transactionRecords.add(transactionRecord);
    }

}
