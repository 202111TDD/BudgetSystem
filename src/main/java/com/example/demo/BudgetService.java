package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BudgetService {


    @Autowired
    IBudgetRepo budgetRepo;

    public double query(LocalDate startDate, LocalDate endDate) {



        return 0;
    }

}
