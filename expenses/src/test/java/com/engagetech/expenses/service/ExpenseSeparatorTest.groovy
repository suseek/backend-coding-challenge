package com.engagetech.expenses.service

import spock.lang.Specification
import spock.lang.Unroll

import static com.neovisionaries.i18n.CurrencyCode.*

class ExpenseSeparatorTest extends Specification {

	ExpenseSeparator expenseSeparator = new ExpenseSeparator()

	@Unroll
	"should separate currency #currency and amount #amount from string #currencyAmountString"() {
		when:
			final result = expenseSeparator.separateCurrencyFromAmount(currencyAmountString)
		then:
			result.getCurrencyCode() == currency
			result.getInitialAmount() == new BigDecimal(amount)
		where:
			currencyAmountString | currency | amount
			"10.5"               | GBP      | "10.5"
			"100.1231 EUR"       | EUR      | "100.1231"
			"0.5USD"             | USD      | "0.5"
	}
}
