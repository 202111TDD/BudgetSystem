package com.example.demo;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    long getOverlappingDays(Budget budget) {
        LocalDate overlappingStart = startDate.isAfter(budget.firstDay())
                ? startDate
                : budget.firstDay();
        LocalDate overlappingEnd = endDate.isBefore(budget.lastDay())
                ? endDate
                : budget.lastDay();
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
