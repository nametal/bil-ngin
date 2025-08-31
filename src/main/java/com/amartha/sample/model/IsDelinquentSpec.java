package com.amartha.sample.model;

import java.time.LocalDate;

public record IsDelinquentSpec(int loanId, LocalDate date) {
}
