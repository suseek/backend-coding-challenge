package com.engagetech.expenses.service

import com.engagetech.expenses.domain.ExchangeRate
import com.engagetech.expenses.domain.Expense
import com.engagetech.expenses.domain.TaxInfo
import com.engagetech.expenses.repository.ExpensesRepository
import spock.lang.Specification
import spock.lang.Unroll

import java.math.RoundingMode

import static com.neovisionaries.i18n.CurrencyCode.*

class ExpenseServiceTest extends Specification {

	final expensesRepository = Mock(ExpensesRepository)
	final taxService = Mock(TaxService)
	final exchangeRateService = Mock(ExchangeRateService)
	ExpenseService expenseService = new ExpenseServiceImpl(expensesRepository, taxService, exchangeRateService)

	@Unroll
	"Should calculate final amount based on tax and exchange rate: #finalAmount (currency #currency)"() {
		given:
			final expense = Expense.builder()
					.initialAmount(new BigDecimal(initialAmount))
					.currency(currency)
					.date(new Date())
					.reason("Testing...")
					.build()

			final Expense result
		when:
			exchangeRateServiceTriggered * exchangeRateService.getGbpExchangeRate(_, _) >>
					new ExchangeRate(rates: ["${currency.toString()}": new BigDecimal(exchangeRate)])
			taxService.getTaxInfo(_) >> new TaxInfo(new BigDecimal(vatRate), new BigDecimal(calculatedVat))
			expenseService.saveExpense(expense)

		then:
			1 * expensesRepository.save(_ as Expense) >> { Expense returnedValue ->
				result = returnedValue
			}
			result.currency == currency
			round(result.vatRate) == round(new BigDecimal(vatRate))
			round(result.calculatedVat) == round(new BigDecimal(calculatedVat))
			round(result.exchangeRate) == round(new BigDecimal(exchangeRate))
			round(result.amount) == round(new BigDecimal(finalAmount))
		where:
			currency | initialAmount | finalAmount | exchangeRate | exchangeRateServiceTriggered | vatRate | calculatedVat
			GBP      | 100           | 100         | 0            | 0                            | 20      | 16.67
			EUR      | 100           | 114         | 1.14         | 1                            | 20      | 16.67
			PLN      | 100           | 21          | 0.21         | 1                            | 20      | 16.67
	}

	private BigDecimal round(BigDecimal value) {
		value.setScale(2, RoundingMode.HALF_UP)
	}
}
