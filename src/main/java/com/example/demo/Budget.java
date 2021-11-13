package com.example.demo;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

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

    public Temporal firstDay() {
        return getYearMonthFromBudget().atDay(1);
    }

    public int getAmount() {
        return amount;
    }

    public Temporal lastDay() {
        return getYearMonthFromBudget().atEndOfMonth();
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

    YearMonth getYearMonthFromBudget() {
        return YearMonth.parse(getYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));
    }

    double dailyAmount() {
        return getAmount() / (double) days();
    }
}
