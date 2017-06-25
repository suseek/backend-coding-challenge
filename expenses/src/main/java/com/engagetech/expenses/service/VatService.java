package com.engagetech.expenses.service;

import com.engagetech.expenses.domain.Rates;
import com.engagetech.expenses.domain.TaxInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.neovisionaries.i18n.CountryCode.UK;

@Service
public class VatService implements TaxService {

	private final Rates rates;

	@Autowired
	public VatService(final Rates rates) {
		this.rates = rates;
	}

	@Override
	public TaxInfo getTaxInfo(final BigDecimal expenseAmount) {
		final BigDecimal ukVatRate = rates.getVatRate(UK);
		final BigDecimal calculatedVat = expenseAmount.subtract(
				expenseAmount.divide(new BigDecimal("1." + ukVatRate), 2, RoundingMode.HALF_UP));

		return new TaxInfo(ukVatRate, calculatedVat);
	}
}
