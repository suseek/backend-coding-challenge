package com.engagetech.expenses.service;

import com.engagetech.expenses.domain.ExchangeRate;
import com.engagetech.expenses.domain.Expense;
import com.engagetech.expenses.domain.TaxInfo;
import com.engagetech.expenses.exception.ThirdPartyServiceException;
import com.engagetech.expenses.repository.ExpensesRepository;
import com.engagetech.expenses.service.dto.ExchangeRateAmount;
import com.neovisionaries.i18n.CurrencyCode;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.neovisionaries.i18n.CurrencyCode.GBP;
import static java.util.Optional.ofNullable;

@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpensesRepository expensesRepository;
	private final TaxService taxService;
	private final ExchangeRateService exchangeRateService;

	@Autowired
	public ExpenseServiceImpl(final ExpensesRepository expensesRepository,
							  final TaxService taxService,
							  final ExchangeRateService exchangeRateService) {
		this.expensesRepository = expensesRepository;
		this.taxService = taxService;
		this.exchangeRateService = exchangeRateService;
	}

	@Override
	public List<Expense> getExpenses() {
		return expensesRepository.findAll();
	}

	@Override
	@Transactional
	public Expense saveExpense(final Expense expense) {
		validateExpenseDate(expense.getDate());

		final ExchangeRateAmount exchangeRateAmount =
				calculateAmount(expense.getInitialAmount(), expense.getDate(), expense.getCurrency());

		expense.setExchangeRate(exchangeRateAmount.getExchangeRate());
		expense.setAmount(exchangeRateAmount.getAmount());

		final TaxInfo taxInfo = taxService.getTaxInfo(expense.getAmount());
		expense.setVatRate(taxInfo.getRate());
		expense.setCalculatedVat(taxInfo.getCalculatedTax());

		return expensesRepository.save(expense);
	}

	private void validateExpenseDate(final Date expenseDate) {
		ofNullable(expenseDate)
				.orElseThrow(() -> new ValidationException("Missing expense date!"));

		if ( expenseDate.after(new Date()) ) {
			throw new ValidationException("The expense cannot happen in the future.");
		}
	}

	private ExchangeRateAmount calculateAmount(final BigDecimal initialAmount,
											   final Date expenseDate,
											   final CurrencyCode currency) {
		if ( GBP != currency ) {
			final String dateString = new SimpleDateFormat("yyyy-MM-dd").format(expenseDate);
			try {
				final ExchangeRate gbpExchangeRate = exchangeRateService
						.getGbpExchangeRate(dateString,
								currency.toString());

				return new ExchangeRateAmount(gbpExchangeRate.getRate(),
						initialAmount.multiply(gbpExchangeRate.getRate()));

			} catch (final FeignException e) {
				LOGGER.warn(e.getMessage());
				throw new ThirdPartyServiceException(e);
			}
		}

		return new ExchangeRateAmount(BigDecimal.ZERO, initialAmount);
	}
}
