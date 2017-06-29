package com.engagetech.expenses.domain;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "expenses")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date")
	@NotNull
	private Date date;

	@Column(name = "amount")
	@NotNull
	private BigDecimal amount;

	@Column(name = "initial_amount")
	@NotNull
	private BigDecimal initialAmount;

	@Column(name = "vat_rate")
	@NotNull
	private BigDecimal vatRate;

	@Column(name = "calculated_vat")
	@NotNull
	private BigDecimal calculatedVat;

	@Column(name = "currency_code")
	@Enumerated(EnumType.STRING)
	@NotNull
	private CurrencyCode currency;

	@Column(name = "exchange_rate")
	private BigDecimal exchangeRate;

	@Column(name = "reason")
	@NotNull
	private String reason;

	@Column(name = "created_at")
	@CreatedDate
	private Date createdAt;
}
