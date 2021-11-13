package com.example.demo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {

    private String yearMonth;
    private int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public int days() {
        return getYearMonthFromBudget().lengthOfMonth();
    }

    public LocalDate firstDay() {
        return getYearMonthFromBudget().atDay(1);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public LocalDate lastDay() {
        return getYearMonthFromBudget().atEndOfMonth();
    }

    Period createPeriod() {
        return new Period(firstDay(), lastDay());
    }

    double dailyAmount() {
        return getAmount() / (double) days();
    }

    YearMonth getYearMonthFromBudget() {
        return YearMonth.parse(getYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));
    }

    double overlappingAmount(Period period) {
        return dailyAmount() * period.getOverlappingDays(createPeriod());
    }
}
