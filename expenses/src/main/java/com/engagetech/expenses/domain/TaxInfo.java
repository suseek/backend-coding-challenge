package com.engagetech.expenses.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxInfo {

	private final BigDecimal rate;
	private final BigDecimal calculatedTax;
}
