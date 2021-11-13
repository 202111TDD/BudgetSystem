package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@SpringBootTest
class BudgetSystemApplicationTests {

    @MockBean
    IBudgetRepo budgetRepo;

    @Autowired
    BudgetService budgetService;

    @Test
    void crossMonth() {
        LocalDate s = LocalDate.parse("20211101", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate e = LocalDate.parse("20211228", DateTimeFormatter.ofPattern("yyyyMMdd"));

        when(budgetRepo.getAll()).thenReturn(asList(new Budget("202111", 3000), new Budget("202112", 6200)));
        Assertions.assertEquals(3000 + 6200 - 600, budgetService.query(s, e));
    }

    @Test
    void errorDate() {
        LocalDate s = LocalDate.parse("20211130", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate e = LocalDate.parse("20211101", DateTimeFormatter.ofPattern("yyyyMMdd"));

        when(budgetRepo.getAll()).thenReturn(asList(new Budget("202111", 3000)));
        Assertions.assertEquals(0, budgetService.query(s, e));
    }

    @Test
    void fullMonth() {
        LocalDate s = LocalDate.parse("20211101", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate e = LocalDate.parse("20211130", DateTimeFormatter.ofPattern("yyyyMMdd"));

        when(budgetRepo.getAll()).thenReturn(asList(new Budget("202111", 3000)));
        Assertions.assertEquals(3000, budgetService.query(s, e));
    }

    @Test
    void singleDay() {
        when(budgetRepo.getAll()).thenReturn(asList(new Budget("202111", 3000)));
        LocalDate start = LocalDate.of(2021, 11, 2);
        LocalDate end = LocalDate.of(2021, 11, 2);
        Assertions.assertEquals(100, budgetService.query(start, end));
    }

    @Test
    void threeMonth() {
        LocalDate s = LocalDate.parse("20211031", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate e = LocalDate.parse("20211204", DateTimeFormatter.ofPattern("yyyyMMdd"));

        when(budgetRepo.getAll()).thenReturn(asList(
                new Budget("202110", 31)
                , new Budget("202111", 3000)
                , new Budget("202112", 3100)
        ));
        Assertions.assertEquals(1 + 3000 + 100 * 4, budgetService.query(s, e));
    }

    @Test
    void zero() {

        when(budgetRepo.getAll()).thenReturn(Collections.emptyList());

        Assertions.assertEquals(0, budgetService.query(LocalDate.now(), LocalDate.now()));
    }
}
