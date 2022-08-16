package com.eatco.fileservice.exceptions;

import com.eatco.fileservice.enums.ErrorCode;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
@Getter
@Setter
public class FUException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private Integer errorCode;
	
	private String message;

	public FUException(String message) {
		super(message);
		this.message = message;
	}

	public FUException(Integer error, String message) {
		super(message);
		this.errorCode = error;
		this.message = message;
	}

	public FUException(Integer error, Exception ex) {
		super(ex);
		this.errorCode = error;
		this.message = ex.getMessage();
	}

	public FUException(Exception ex) {
		super(ex);
		this.message = ex.getMessage();
	}

	public FUException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode.getCode();
		this.message = message;
	}

	public FUException(ErrorCode errorCode, Exception ex) {
		super(ex);
		this.errorCode = errorCode.getCode();
		this.message = ex.getCause().getMessage();

	}
}
