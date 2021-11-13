package com.example.demo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    long getOverlappingDays(Budget budget) {
        LocalDate overlappingStart;
        LocalDate overlappingEnd;
        if (YearMonth.from(getStartDate()).format(DateTimeFormatter.ofPattern("yyyyMM")).equals(budget.getYearMonth())) {
            overlappingStart = getStartDate();
            overlappingEnd = budget.lastDay();
        } else if (YearMonth.from(getEndDate()).format(DateTimeFormatter.ofPattern("yyyyMM")).equals(budget.getYearMonth())) {
            overlappingStart = budget.firstDay();
            overlappingEnd = getEndDate();
        } else {
            overlappingStart = budget.firstDay();
            overlappingEnd = budget.lastDay();
        }
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
