package com.jope.tinyLedger.service;

import java.math.BigDecimal;
import java.util.List;

public interface TinyLedgerService {
    List<String> getTransactionRecords();
    BigDecimal getLedgerBalance();
    void withdrawFromLedger(double amount);
    void depositToLedger(double amount);
}
