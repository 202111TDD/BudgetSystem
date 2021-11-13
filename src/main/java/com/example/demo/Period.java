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

    long getOverlappingDays(Period another) {
        if (isInvalid()) {
            return 0;
        }
        if (hasNoOverlapping(another)) {
            return 0;
        }
        LocalDate firstDay = another.startDate;
        LocalDate lastDay = another.endDate;
        LocalDate overlappingStart = startDate.isAfter(firstDay)
                ? startDate
                : firstDay;
        LocalDate overlappingEnd = endDate.isBefore(lastDay)
                ? endDate
                : lastDay;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }

    private boolean hasNoOverlapping(Period another) {
        return endDate.isBefore(another.startDate) || startDate.isAfter(another.endDate);
    }

    private boolean isInvalid() {
        return startDate.isAfter(endDate);
    }
}
