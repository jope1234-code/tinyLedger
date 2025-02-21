package com.jope.tinyLedger;

import com.jope.tinyLedger.service.TinyLedgerService;
import com.jope.tinyLedger.service.TinyLedgerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertThrows;

public class TinyLedgerServiceImplTest {

    private final TinyLedgerService service = new TinyLedgerServiceImpl();

    @Test
    void checkCantWithdrawNegativeAmount(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.withdrawFromLedger(-23.2));
        Assertions.assertEquals("amount: -23.2 is not a valid input", exception.getMessage());
    }

    @Test
    void checkCantDepositNegativeAmount(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.depositToLedger(-23.2));
        Assertions.assertEquals("amount: -23.2 is not a valid input", exception.getMessage());
    }

    // The next tests are somewhat skewwed as I've had to create the repo manually rather than via a DB or some such - so using the values already setup rather than mocking


    @Test
    void checkCantWithdrawMoreThanLedgerAmount(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.withdrawFromLedger(2001));
        Assertions.assertEquals("2001.0 is larger than the ledger total - so cannot be withdrawn", exception.getMessage());
    }

    @Test
    void checkCanDeposit(){
        BigDecimal intialBalance = service.getLedgerBalance();
        service.depositToLedger(10.00);
        BigDecimal expected = intialBalance.add(new BigDecimal("10.00"));
        Assertions.assertEquals(service.getLedgerBalance(), expected);
        Assertions.assertTrue(service.getTransactionRecords().contains("DEPOSITING 10.00"));
    }

    @Test
    void checkCanWithdraw(){
        BigDecimal intialBalance = service.getLedgerBalance();
        service.withdrawFromLedger(10.00);
        BigDecimal expected = intialBalance.subtract(new BigDecimal("10.00"));
        Assertions.assertEquals(service.getLedgerBalance(), expected);
        Assertions.assertTrue(service.getTransactionRecords().contains("WITHDRAWING 10.00"));
    }


    @Test
    void checkHandlesRoundingDepositing(){
        BigDecimal intialBalance = service.getLedgerBalance();
        service.depositToLedger(10.199);
        BigDecimal expected = intialBalance.add(new BigDecimal("10.20"));
        Assertions.assertEquals(service.getLedgerBalance(), expected);
        Assertions.assertTrue(service.getTransactionRecords().contains("DEPOSITING 10.20"));
    }

    @Test
    void checkHandlesRoundingWithdrawing(){
        BigDecimal intialBalance = service.getLedgerBalance();
        service.withdrawFromLedger(10.199);
        BigDecimal expected = intialBalance.subtract(new BigDecimal("10.20"));
        Assertions.assertEquals(service.getLedgerBalance(), expected);
        Assertions.assertTrue(service.getTransactionRecords().contains("WITHDRAWING 10.20"));
    }



}
