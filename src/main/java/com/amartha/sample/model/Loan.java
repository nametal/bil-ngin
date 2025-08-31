package com.amartha.sample.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Loan {
    private static final AtomicInteger lids = new AtomicInteger(1);

    private final int id;
    private final BigDecimal totalAmount;
    private final BigDecimal annualRate;
    private final LocalDate createdDate;
    private final LocalDate dueDate;

    public Loan(BigDecimal totalAmount, BigDecimal annualRate, LocalDate createdDate, LocalDate dueDate) {
        this.id = lids.getAndIncrement();
        this.totalAmount = totalAmount;
        this.annualRate = annualRate;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getAnnualRate() {
        return annualRate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", annualRate=" + annualRate +
                ", createdDate=" + createdDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
