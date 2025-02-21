package com.jope.tinyLedger.service;

import com.jope.tinyLedger.repository.TinyLedgerRepository;
import com.jope.tinyLedger.util.TinyLedgerUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TinyLedgerServiceImpl implements TinyLedgerService{

    private final TinyLedgerRepository tinyLedgerRepository;
    private static final ReentrantLock lock = new ReentrantLock();
    public TinyLedgerServiceImpl(){

        //Temporarily setting up a repo, this would all be DB or equivalent in the real world
        List<String> setupTransactions = new ArrayList<>();
        setupTransactions.add("DEPOSITING 2000.00");
        setupTransactions.add("WITHDRAWING 500.00");
        setupTransactions.add("WITHDRAWING 500.00");

        this.tinyLedgerRepository = new TinyLedgerRepository(new BigDecimal("1000.00"),setupTransactions);
    }

    @Override
    public List<String> getTransactionRecords() {
        lock.lock();
        try {
            return tinyLedgerRepository.getTransactionRecords();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public BigDecimal getLedgerBalance() {
        lock.lock();
        try {
        return tinyLedgerRepository.getLedgerBalance();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdrawFromLedger(double amount) {
        lock.lock();
        try {
            BigDecimal bigDecimalAmount = TinyLedgerUtil.convertToBigDecimal(amount);
            BigDecimal newLedgerTotal = tinyLedgerRepository.getLedgerBalance().subtract(bigDecimalAmount);
            if(newLedgerTotal.compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException(amount + " is larger than the ledger total - so cannot be withdrawn");
            } else {
                tinyLedgerRepository.updateLedger(newLedgerTotal, "WITHDRAWING " + bigDecimalAmount);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void depositToLedger(double amount) {
        lock.lock();
        try {
            BigDecimal bigDecimalAmount = TinyLedgerUtil.convertToBigDecimal(amount);
            BigDecimal newLedgerTotal = tinyLedgerRepository.getLedgerBalance().add(bigDecimalAmount);
            tinyLedgerRepository.updateLedger(newLedgerTotal, "DEPOSITING " + bigDecimalAmount);
        } finally {
            lock.unlock();
        }
    }
}
