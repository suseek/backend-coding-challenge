package com.engagetech.expenses.repository;

import com.engagetech.expenses.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Long> {
}
