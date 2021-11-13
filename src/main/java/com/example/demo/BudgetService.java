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

        Period period = new Period(startDate, endDate);
        return budgetRepo.getAll()
                .stream()
                .mapToDouble(budget -> budget.overlappingAmount(period))
                .sum();
    }
}
