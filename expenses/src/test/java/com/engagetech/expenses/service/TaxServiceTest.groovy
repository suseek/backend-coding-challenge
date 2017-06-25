package com.engagetech.expenses.service

import com.engagetech.expenses.domain.Rates
import com.engagetech.expenses.domain.TaxInfo
import com.neovisionaries.i18n.CountryCode
import spock.lang.Specification
import spock.lang.Unroll

import java.math.RoundingMode

class TaxServiceTest extends Specification {

	@Unroll
	"should calculate VAT based on the initial value of #initialValue and vat rate #vatRate : #calculatedVat"() {
		given:
			final rates = new Rates()
			rates.setVat(["UK": vatRate])
			final vatService = new VatService(rates)
			final amount = new BigDecimal(initialValue)

		when:
			final TaxInfo taxInfo = vatService.getTaxInfo(amount)

		then:
			taxInfo.getRate() == rates.getVatRate(CountryCode.UK)
			taxInfo.getCalculatedTax().setScale(2, RoundingMode.HALF_UP) ==
					new BigDecimal(calculatedVat).setScale(2, RoundingMode.HALF_UP)

		where:
			initialValue | vatRate | calculatedVat
			100          | 20      | 16.67
	}
}
