package com.amartha.sample.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Bill {
    private static final AtomicInteger bids = new AtomicInteger(1);

    private final int id;
    private final int loanId;
    private final LocalDate dueDate;
    private final BigDecimal amount;
    private BillStatus status;
    private LocalDate paidDate;

    public Bill(int loanId, LocalDate dueDate, BigDecimal amount, BillStatus status, LocalDate paidDate) {
        this.id = bids.getAndIncrement();
        this.loanId = loanId;
        this.dueDate = dueDate;
        this.amount = amount;
        this.status = status;
        this.paidDate = paidDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", loanId=" + loanId +
                ", dueDate=" + dueDate +
                ", amount=" + amount +
                ", status=" + status +
                ", paidDate=" + paidDate +
                '}';
    }
}
