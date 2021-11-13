package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@SpringBootTest
class BudgetSystemApplicationTests {

	@MockBean
	IBudgetRepo budgetRepo;

	@Autowired
	BudgetService budgetService;

	@Test
	void zero() {

		when(0)

		Assertions.assertEquals(0,budgetService.query(LocalDate.now(), LocalDate.now()));
	}



}
