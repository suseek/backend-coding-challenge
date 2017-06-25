package com.engagetech.expenses.web;

import com.engagetech.expenses.domain.Expense;
import com.engagetech.expenses.service.dto.CurrencyInitialAmount;
import com.engagetech.expenses.service.ExpenseSeparator;
import com.engagetech.expenses.service.ExpenseService;
import com.engagetech.expenses.web.request.SaveExpenseRequest;
import com.engagetech.expenses.web.response.ExpenseResponse;
import io.swagger.annotations.Api;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.engagetech.expenses.web.ApiParameters.API_V1;
import static com.engagetech.expenses.web.ApiParameters.EXPENSES;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@CrossOrigin
@RestController
@Api(description = "Expenses")
@RequestMapping(value = API_V1 + EXPENSES, produces = APPLICATION_JSON_UTF8_VALUE)
public class ExpenseController {

	private final ExpenseService expenseService;

	private final MapperFacade mapper;

	private final ExpenseSeparator separator;

	@Autowired
	public ExpenseController(final ExpenseService expenseService,
							 final MapperFacade mapper,
							 final ExpenseSeparator separator) {
		this.expenseService = expenseService;
		this.mapper = mapper;
		this.separator = separator;
	}

	@GetMapping
	public List<ExpenseResponse> getExpenses() {
		return mapper.mapAsList(expenseService.getExpenses(), ExpenseResponse.class);
	}

	@PostMapping
	public ExpenseResponse saveExpense(@Valid @RequestBody final SaveExpenseRequest request) throws ParseException {
		final CurrencyInitialAmount currencyAmount = separator.separateCurrencyFromAmount(request.getAmount());

		final Expense expense =
				Expense.builder()
						.date(Date.from(
								request.getDate()
										.atStartOfDay(ZoneId.systemDefault())
										.toInstant()))
						.reason(request.getReason())
						.currency(currencyAmount.getCurrencyCode())
						.initialAmount(currencyAmount.getInitialAmount())
						.build();

		return mapper.map(expenseService.saveExpense(expense), ExpenseResponse.class);
	}
}
