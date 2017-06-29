package com.engagetech.expenses.service;

import com.engagetech.expenses.domain.ExchangeRate;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient("fixer")
public interface ExchangeRateService {

	@GetMapping("/{date}?base={currencyCode}&symbols=GBP")
	ExchangeRate getGbpExchangeRate(@PathVariable("date") final String date,
									@PathVariable("currencyCode") final String currencyCode);
}


