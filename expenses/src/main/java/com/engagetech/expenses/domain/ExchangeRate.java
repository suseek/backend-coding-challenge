package com.engagetech.expenses.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;
import lombok.Data;

import java.math.BigDecimal;
import java.util.TreeMap;

@Data
public class ExchangeRate {
	private String base;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String date;

	private TreeMap<String, BigDecimal> rates = Maps.newTreeMap();

	public BigDecimal getRate() {
		return rates.firstEntry().getValue();
	}
}
