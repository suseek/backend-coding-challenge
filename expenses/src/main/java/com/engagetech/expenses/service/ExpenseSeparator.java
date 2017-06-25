package com.engagetech.expenses.service;

import com.engagetech.expenses.service.dto.CurrencyInitialAmount;
import com.neovisionaries.i18n.CurrencyCode;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.math.BigDecimal;

import static com.neovisionaries.i18n.CurrencyCode.GBP;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.matches;

@Component
public class ExpenseSeparator {

	public CurrencyInitialAmount separateCurrencyFromAmount(final String amountCurrencyString) {
		final String AMOUNT_REGEX = "\\d+(\\.\\d+?)?";
		final CurrencyCode currency;
		final String initialAmount;

		if ( matches(AMOUNT_REGEX, amountCurrencyString) ) {
			currency = GBP;
			initialAmount = amountCurrencyString;
		} else {
			final String currencyString = amountCurrencyString.replaceAll(AMOUNT_REGEX, "").trim();
			currency = ofNullable(CurrencyCode.getByCode(currencyString))
					.orElseThrow(() ->
							new ValidationException("Currency " + currencyString + " is not supported."));

			initialAmount = of(amountCurrencyString.replaceAll(currency.toString(), "").trim())
					.filter(el -> !el.isEmpty())
					.orElseThrow(() ->
							new ValidationException("You need to specify the correct expense amount."));

		}

		return new CurrencyInitialAmount(currency, new BigDecimal(initialAmount));
	}
}
