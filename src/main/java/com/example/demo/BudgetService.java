package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class BudgetService {

    @Autowired
    IBudgetRepo budgetRepo;

    public double query(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return 0;
        }

        List<Budget> budgets = budgetRepo.getAll();
        double totalAmount = 0;
        Period period = new Period(startDate, endDate);
        for (Budget budget : budgets) {
            totalAmount += budget.overlappingAmount(period);
        }
        return totalAmount;
    }
}
