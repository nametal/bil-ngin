package com.amartha.sample.service;

import com.amartha.sample.accessor.BillingAccessor;
import com.amartha.sample.accessor.BillingAccessorImpl;
import com.amartha.sample.model.Bill;
import com.amartha.sample.model.CreateLoanSpec;
import com.amartha.sample.model.IsDelinquentSpec;
import com.amartha.sample.model.Loan;
import com.amartha.sample.model.MakePaymentSpec;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBillingService {
    private final BillingService target;

    public TestBillingService() {
        List<Loan> loans = new ArrayList<>();
        Map<Integer, List<Bill>> billMap = new HashMap<>();
        BillingAccessor accessor = new BillingAccessorImpl(loans, billMap);
        this.target = new BillingServiceImpl(accessor);
    }

    @Test
    public void test() {
        CreateLoanSpec createLoanSpec =
                new CreateLoanSpec(50, BigDecimal.valueOf(5000000), BigDecimal.valueOf(0.1));
        int loanId = target.createLoan(createLoanSpec);

        BigDecimal outstanding1 = target.getOutstanding(loanId);
        Assert.assertEquals(outstanding1, BigDecimal.valueOf(5500000.0));

        LocalDate today = LocalDate.now();
        LocalDate threeDaysFromNow = today.plusDays(10);
        IsDelinquentSpec isDelinquentSpec = new IsDelinquentSpec(loanId, threeDaysFromNow);
        boolean delinquent = target.isDelinquent(isDelinquentSpec);
        Assert.assertTrue(delinquent);

        MakePaymentSpec makePaymentSpec =
                new MakePaymentSpec(loanId, today.plusDays(1), BigDecimal.valueOf(110000));
        boolean paid = target.makePayment(makePaymentSpec);
        Assert.assertTrue(paid);

        boolean notDelinquent = target.isDelinquent(isDelinquentSpec);
        Assert.assertFalse(notDelinquent);
    }
}
