package com.engagetech.expenses.domain;

import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "rates")
public class Rates {

	private Map<String, Integer> vat;

	public BigDecimal getVatRate(final CountryCode countryCode) {
		return new BigDecimal(vat.get(countryCode.toString()));
	}
}
