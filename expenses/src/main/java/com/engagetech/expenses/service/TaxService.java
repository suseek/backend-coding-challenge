package com.engagetech.expenses.service;


import com.engagetech.expenses.domain.TaxInfo;

import java.math.BigDecimal;

public interface TaxService {

	TaxInfo getTaxInfo(final BigDecimal expenseAmount);
}
