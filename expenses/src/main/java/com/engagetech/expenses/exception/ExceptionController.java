package com.engagetech.expenses.exception;

import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Map;

@ResponseBody
@ControllerAdvice
public class ExceptionController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	/**
	 * Method catch all internal exception and return JSON object with information.
	 *
	 * @param e exception
	 * @return JSON object with error information
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> baseException(HttpServletRequest req, Exception e) throws Exception {
		LOGGER.error(e.getMessage(), e);
		if ( AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null )
			throw e;
		LOGGER.error("Request: " + req.getRequestURL() + " raised " + e);
		return Collections.singletonMap("error", "Internal Server Error!");
	}

	@ExceptionHandler(value = {ValidationException.class, MethodArgumentNotValidException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Map<String, String> badRequestException(Exception e) {
		return Collections.singletonMap("error", e.getMessage());
	}
}
