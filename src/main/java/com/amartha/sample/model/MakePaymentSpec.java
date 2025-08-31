package com.amartha.sample.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MakePaymentSpec(int loanId, LocalDate date, BigDecimal amount) {
}
