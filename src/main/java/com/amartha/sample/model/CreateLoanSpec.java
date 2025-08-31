package com.amartha.sample.model;

import java.math.BigDecimal;

public record CreateLoanSpec(int tenureWeeks, BigDecimal loanAmount, BigDecimal annualRate) {
}
