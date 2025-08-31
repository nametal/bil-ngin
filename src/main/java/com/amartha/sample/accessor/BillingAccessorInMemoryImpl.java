package com.amartha.sample.accessor;

import com.amartha.sample.model.Bill;
import com.amartha.sample.model.BillStatus;
import com.amartha.sample.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingAccessorInMemoryImpl implements BillingAccessor {
    private final List<Loan> loans = new ArrayList<>();
    private final Map<Integer, List<Bill>> billMap = new HashMap<>();

    @Override
    public void insertLoan(Loan loan) {
        loans.add(loan);
    }

    @Override
    public void insertBills(List<Bill> bills) {
        Bill first = bills.getFirst();
        billMap.put(first.getLoanId(), bills);
    }

    @Override
    public void updateBillsToPaid(List<String> billIds, LocalDate date) {

    }

    @Override
    public List<Loan> getLoans() {
        return loans;
    }

    @Override
    public List<Bill> getBills(int loanId) {
        return billMap.get(loanId);
    }

    @Override
    public List<Bill> getPendingBills(int loanId, LocalDate date) {
        List<Bill> bills = getBills(loanId);
        if (bills == null) {
            return List.of();
        }
        return bills.stream()
                .filter(b -> b.getStatus() == BillStatus.UNPAID)
                .filter(b -> ChronoUnit.DAYS.between(date, b.getDueDate()) < 7)
                .toList();
    }

    @Override
    public BigDecimal getOutstandingAmount(int loanId) {
        List<Bill> bills = getBills(loanId);
        if (bills == null) {
            return null;
        }
        return bills.stream().filter(b -> b.getStatus() == BillStatus.UNPAID)
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
