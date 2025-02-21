package com.jope.tinyLedger.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TinyLedgerUtil {

    // I'm treating any amount 0 or less as a non valid number and doing rounding based on half_even
    public static BigDecimal convertToBigDecimal(double amount){
        if(amount > 0) {
            return new BigDecimal(amount)
                    .setScale(2, RoundingMode.HALF_EVEN);

        } else {
            throw new IllegalArgumentException("amount: " + amount + " is not a valid input");
        }
    }
}
