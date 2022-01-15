package com.test.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.accountservice.constants.Constants.ErrorsTypes;
import com.test.accountservice.dto.ResponseDto;
import com.test.accountservice.errors.AmountInvalidException;
import com.test.accountservice.errors.ExistException;
import com.test.accountservice.errors.NotExistException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * exist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(ExistException.class)
	public ResponseDto exist(ExistException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.INFORMATION_EXIST.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(NotExistException.class)
	public ResponseDto notExist(NotExistException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.INFORMATION_NOT_EXIST.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * amountInvalid method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(AmountInvalidException.class)
	public ResponseDto amountInvalid(AmountInvalidException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.AMOUNT_INVALID.getErrorsType(),ex.getMessage());
		return response;
	}
	
	
}
