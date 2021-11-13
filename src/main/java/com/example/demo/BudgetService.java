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

        YearMonth startYearMonth = YearMonth.from(startDate);
        YearMonth endYearMonth = YearMonth.from(endDate);

        // 起大於訖 return 0
        // 年月一樣
        // 這個月有幾天，算出"每天的預算"
        // 算出"區間有幾天"
        // 算出"區間預算"
        // 年月不同
        // 取得區間內所有 Budget
        // 算出"每個budget的每天的預算"
        // 算出區間內"各月的天數"
        //"各月的天數" X "每個budget的每天的預算"

        List<Budget> budgets = budgetRepo.getAll();
        if (startYearMonth.equals(endYearMonth)) {
            Optional<Budget> budget = budgets.stream()
                    .filter(b -> startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).equals(b.getYearMonth()))
                    .findFirst();

            double amount = budget.map(Budget::getAmount).orElse(0);
            double oneDayBudget = amount / (double) startYearMonth.lengthOfMonth();
            int day = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            return oneDayBudget * day;
        } else {
            double totalAmount = 0;
            for (Budget budget : budgets) {
                YearMonth yearMonthFromBudget = budget.getYearMonthFromBudget();
                if (startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).equals(budget.getYearMonth())) {
                    int startDays = startYearMonth.lengthOfMonth() - startDate.getDayOfMonth() + 1;
                    totalAmount += budget.dailyAmount() * startDays;
                } else if (endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).equals(budget.getYearMonth())) {
                    int endDays = endDate.getDayOfMonth();
                    totalAmount += budget.dailyAmount() * endDays;
                } else if (yearMonthFromBudget.isAfter(startYearMonth) && yearMonthFromBudget.isBefore(endYearMonth)) {
                    totalAmount += budget.dailyAmount() * budget.days();
//                    totalAmount += budget.getAmount();
                }
            }
            return totalAmount;
        }
    }
}
