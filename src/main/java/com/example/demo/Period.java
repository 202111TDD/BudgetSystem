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

        Period another = new Period(budget.firstDay(), budget.lastDay());
        LocalDate firstDay = budget.firstDay();
        LocalDate lastDay = budget.lastDay();
        LocalDate overlappingStart = startDate.isAfter(firstDay)
                ? startDate
                : firstDay;
        LocalDate overlappingEnd = endDate.isBefore(lastDay)
                ? endDate
                : lastDay;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
