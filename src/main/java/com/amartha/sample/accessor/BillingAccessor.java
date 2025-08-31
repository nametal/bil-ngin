package com.amartha.sample.accessor;

import com.amartha.sample.model.Bill;
import com.amartha.sample.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BillingAccessor {
    void insertLoan(Loan loan);

    void insertBills(List<Bill> bills);

    void updateBillsToPaid(List<String> billIds, LocalDate date);

    List<Loan> getLoans();

    List<Bill> getBills(int loanId);

    List<Bill> getPendingBills(int loanId, LocalDate date);

    BigDecimal getOutstandingAmount(int loanId);
}
