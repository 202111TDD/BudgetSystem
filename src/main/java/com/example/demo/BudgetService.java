package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<Budget> budgets = budgetRepo.getAll();
        List<Budget> middleBudgets = budgets.stream().filter(b ->
                {
                    YearMonth recordYearMonth = YearMonth.parse(b.getYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));
                    return recordYearMonth.isAfter(startYearMonth) && recordYearMonth.isBefore(endYearMonth);

//                    return (startYearMonth.isBefore(recordYearMonth) || startYearMonth.compareTo(recordYearMonth) == 0)
//                            && (endYearMonth.isAfter(recordYearMonth) || endYearMonth.compareTo(recordYearMonth) == 0);
                }
        ).collect(Collectors.toList());
//        if (budgets.isEmpty()) {
//            return 0;
//        }

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

//        startDate.lengthOfMonth()

        if (startYearMonth.equals(endYearMonth)) {
            Optional<Budget> budget = budgets.stream()
                    .filter(b -> startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).equals(b.getYearMonth()))
                    .findFirst();

            double amount = budget.map(Budget::getAmount).orElse(0);
            double oneDayBudget = amount / (double) startYearMonth.lengthOfMonth();
            int day = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            return oneDayBudget * day;
        } else {
            Optional<Budget> startBudget = budgets.stream()
                    .filter(b -> startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).equals(b.getYearMonth()))
                    .findFirst();
            double amountOfStart = startBudget.map(Budget::getAmount).orElse(0) / (double) startYearMonth.lengthOfMonth();
//            double startBudget = budgets.getOrDefault(startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")), 0) / (double) startYearMonth.lengthOfMonth();
//            budgets.remove(startBudget);
//            budgets.remove(startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")));
            int startDays = startYearMonth.lengthOfMonth() - startDate.getDayOfMonth() + 1;
            double amountOfStartBudget = amountOfStart * startDays;

            Optional<Budget> endBudget = budgets.stream()
                    .filter(b -> endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")).equals(b.getYearMonth()))
                    .findFirst();
            double amountOfEnd = endBudget.map(Budget::getAmount).orElse(0) / (double) endYearMonth.lengthOfMonth();
//            double endBudget = budgets.getOrDefault(endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")), 0) / (double) endYearMonth.lengthOfMonth();
//            budgets.remove(endBudget);
            int endDays = endDate.getDayOfMonth();
            double amountOfEndBudget = amountOfEnd * endDays;

            double amountOfMiddleBudgets = middleBudgets.stream().map(Budget::getAmount).reduce(0, Integer::sum);

            return amountOfStartBudget + amountOfEndBudget + amountOfMiddleBudgets;
        }
    }
}
