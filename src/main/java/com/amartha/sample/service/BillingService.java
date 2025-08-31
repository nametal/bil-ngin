package com.amartha.sample.service;

import com.amartha.sample.model.Bill;
import com.amartha.sample.model.CreateLoanSpec;
import com.amartha.sample.model.IsDelinquentSpec;
import com.amartha.sample.model.Loan;
import com.amartha.sample.model.MakePaymentSpec;

import java.math.BigDecimal;
import java.util.List;

public interface BillingService {
    int createLoan(CreateLoanSpec spec);

    BigDecimal getOutstanding(int loanId);

    boolean isDelinquent(IsDelinquentSpec spec);

    boolean makePayment(MakePaymentSpec spec);

    List<Bill> getBills(int loanId);

    List<Loan> getLoans();
}
