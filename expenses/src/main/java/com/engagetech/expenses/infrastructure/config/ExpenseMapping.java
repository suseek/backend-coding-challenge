package com.engagetech.expenses.infrastructure.config;

import com.engagetech.expenses.domain.Expense;
import com.engagetech.expenses.web.request.SaveExpenseRequest;
import com.engagetech.expenses.web.response.ExpenseResponse;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapping implements OrikaMapperFactoryConfigurer {
	@Override
	public void configure(final MapperFactory orikaMapperFactory) {
		orikaMapperFactory.classMap(SaveExpenseRequest.class, Expense.class)
				.exclude("date")
				.exclude("amount")
				.byDefault()
				.register();

		orikaMapperFactory.classMap(Expense.class, ExpenseResponse.class)
				.field("calculatedVat", "vat")
				.byDefault()
				.register();
	}

}
