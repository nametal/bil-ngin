package com.amartha.sample.accessor;

import com.amartha.sample.model.Bill;
import com.amartha.sample.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BillingAccessor {
    int insertLoan(Loan loan);

    int insertBills(List<Bill> bills);

    List<Loan> getLoans();

    List<Bill> getBills(int loanId);

    List<Bill> getPendingBills(int loanId, LocalDate date);

    BigDecimal getOutstandingAmount(int loanId);
}
