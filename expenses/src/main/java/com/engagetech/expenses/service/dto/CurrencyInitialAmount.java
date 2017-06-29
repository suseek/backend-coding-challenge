package com.engagetech.expenses.service.dto;


import com.neovisionaries.i18n.CurrencyCode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyInitialAmount {

	private final CurrencyCode currencyCode;
	private final BigDecimal initialAmount;
}
