package com.engagetech.expenses.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static com.engagetech.expenses.web.ApiParameters.DATE_PATTERN;

@Data
@ApiModel
public class SaveExpenseRequest {

	@ApiModelProperty(example = "15 EUR", required = true)
	@NotNull
	private String amount;

	@ApiModelProperty(example = "10/01/2017", required = true)
	@JsonFormat(pattern = DATE_PATTERN)
	@NotNull
	private LocalDate date;

	@ApiModelProperty(example = "An example reason!", required = true)
	@NotNull
	@Size(max = 1000)
	private String reason;
}
