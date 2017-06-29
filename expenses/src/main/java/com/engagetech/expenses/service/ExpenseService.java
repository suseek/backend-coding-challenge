package com.engagetech.expenses.service;

import com.engagetech.expenses.domain.Expense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface ExpenseService {

	Logger LOGGER = LoggerFactory.getLogger(ExpenseService.class);

	List<Expense> getExpenses();

	Expense saveExpense(Expense expense);
}
