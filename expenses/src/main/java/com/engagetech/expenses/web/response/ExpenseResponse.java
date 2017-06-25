package com.engagetech.expenses.web.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import static com.engagetech.expenses.web.ApiParameters.DATE_PATTERN;

@Data
@ApiModel
public class ExpenseResponse {

	@JsonFormat(pattern = DATE_PATTERN)
	@ApiModelProperty(example = "10/01/2017")
	private Date date;

	@ApiModelProperty(example = "100")
	private BigDecimal amount;

	@ApiModelProperty(example = "16.67")
	private BigDecimal vat;

	@ApiModelProperty(example = "An important expense.")
	private String reason;
}
