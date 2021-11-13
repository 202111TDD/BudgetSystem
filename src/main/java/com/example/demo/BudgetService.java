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

        Map<String, Integer> budgets = budgetRepo.getAll().stream().filter(b ->
                {
                    YearMonth recordYearMonth = YearMonth.parse(b.getYearMonth(), DateTimeFormatter.ofPattern("yyyyMM"));

                    return (startYearMonth.isBefore(recordYearMonth) || startYearMonth.compareTo(recordYearMonth) == 0)
                            && (endYearMonth.isAfter(recordYearMonth) || endYearMonth.compareTo(recordYearMonth) == 0);
                }
        ).collect(Collectors.toMap(Budget::getYearMonth, Budget::getAmount));

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
            if (budgets.isEmpty()) {
                return 0;
            }
            double oneDayBudget = budgets.getOrDefault(startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")), 0) / startYearMonth.lengthOfMonth();
            int day = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            return oneDayBudget * day;
        } else {
            double startBudget = budgets.getOrDefault(startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")), 0) / startYearMonth.lengthOfMonth();
            budgets.remove(startYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")));
            int startDays = startYearMonth.lengthOfMonth() - startDate.getDayOfMonth() + 1;
            double aa = startBudget * startDays;

            double endBudget = budgets.getOrDefault(endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")), 0) / endYearMonth.lengthOfMonth();
            budgets.remove(endYearMonth.format(DateTimeFormatter.ofPattern("yyyyMM")));
            int endDays = endDate.getDayOfMonth();
            double bb = endBudget * endDays;

            double cc = budgets.values().stream().reduce(0, Integer::sum);

            return aa + bb + cc;
        }
    }
}
