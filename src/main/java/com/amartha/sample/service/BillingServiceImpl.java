package com.amartha.sample.service;

import com.amartha.sample.accessor.BillingAccessor;
import com.amartha.sample.model.Bill;
import com.amartha.sample.model.BillStatus;
import com.amartha.sample.model.CreateLoanSpec;
import com.amartha.sample.model.IsDelinquentSpec;
import com.amartha.sample.model.Loan;
import com.amartha.sample.model.MakePaymentSpec;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillingServiceImpl implements BillingService {
    private final BillingAccessor accessor;

    public BillingServiceImpl(BillingAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public int createLoan(CreateLoanSpec spec) {
        LocalDate today = LocalDate.now();
        Loan loan = new Loan(
                spec.loanAmount(),
                spec.annualRate(),
                today,
                today.plusWeeks(spec.tenureWeeks())
        );

        BigDecimal billAmount =
                spec.loanAmount().multiply(BigDecimal.ONE.add(spec.annualRate()))
                        .divide(BigDecimal.valueOf(spec.tenureWeeks()), RoundingMode.HALF_UP);
        LocalDate nextDueDate = today;
        List<Bill> bills = new ArrayList<>();
        for (int i = 1; i <= spec.tenureWeeks(); i++) {
            nextDueDate = nextDueDate.plusWeeks(1);
            Bill bill = new Bill(
                    loan.getId(),
                    nextDueDate,
                    billAmount,
                    BillStatus.UNPAID,
                    null
            );
            bills.add(bill);
        }

        accessor.insertLoan(loan);
        accessor.insertBills(bills);

        return loan.getId();
    }

    @Override
    public BigDecimal getOutstanding(int loanId) {
        return accessor.getOutstandingAmount(loanId);
    }

    @Override
    public boolean isDelinquent(IsDelinquentSpec spec) {
        List<Bill> pendingBills = accessor.getPendingBills(spec.loanId(), spec.date());
        return pendingBills.size() >= 2;
    }

    @Override
    public boolean makePayment(MakePaymentSpec spec) {
        List<Bill> bills = getBills(spec.loanId());
        if (bills == null) {
            System.err.println("Loan not found: " + spec.loanId());
            return false;
        }
        List<Bill> pendingBills = accessor.getPendingBills(spec.loanId(), spec.date());
        BigDecimal pendingAmount = pendingBills.stream().map(Bill::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (spec.amount().compareTo(pendingAmount) < 0) {
            System.out.printf("Amount insufficient: %s < %s%n", spec.amount(), pendingAmount);
            return false;
        } else if (spec.amount().compareTo(pendingAmount) > 0) {
            System.out.printf("Amount exceed: %s > %s%n", spec.amount(), pendingAmount);
            return false;
        }
        // update status
        pendingBills.forEach(bill -> {
            bill.setStatus(BillStatus.PAID);
            bill.setPaidDate(spec.date());
        });
        return true;
    }

    @Override
    public List<Bill> getBills(int loanId) {
        return accessor.getBills(loanId);
    }

    @Override
    public List<Loan> getLoans() {
        return accessor.getLoans();
    }
}
