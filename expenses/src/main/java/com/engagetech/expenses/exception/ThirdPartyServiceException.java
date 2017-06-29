package com.engagetech.expenses.exception;


import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ThirdPartyServiceException extends RuntimeException {
	public ThirdPartyServiceException(final FeignException e) {
		super("Couldn't connect to the third party service!");
	}
}
