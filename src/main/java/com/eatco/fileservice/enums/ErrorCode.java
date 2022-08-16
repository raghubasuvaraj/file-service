package com.eatco.fileservice.enums;

/**
 * Throw exception along with the custom http status
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
public enum ErrorCode {

	INTERNAL_SERVER_ERROR(500), NOT_FOUND(404), ACCESS_DENIED(403), BAD_REQUEST(400), UNAUTHORIZED(401),
	METHOD_NOT_SUPPORTED(405), INVALID_OPERATION(412), INVALID_CUSTOMER(413), CONSTRAINT_VIOLATION(101), 
	CONFLICT(409);

	private int code;

	ErrorCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
