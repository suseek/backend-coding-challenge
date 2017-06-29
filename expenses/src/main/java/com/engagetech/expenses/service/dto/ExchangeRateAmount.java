package com.engagetech.expenses.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRateAmount {

	private final BigDecimal exchangeRate;
	private final BigDecimal amount;
}
