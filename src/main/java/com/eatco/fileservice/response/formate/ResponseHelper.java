package com.eatco.fileservice.response.formate;

import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.exceptions.FUException;

/**
 * Common response format builder
 * @author Arya C Achari
 * @since 0.0.1
 *
 */
public class ResponseHelper {
	
	/**
	 * Use when the API return response class.
	 * 
	 * @param response
	 * @param data
	 * @param successMessage
	 * @param errorMessage
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static EatcoResponse createResponse(EatcoResponse response, Object data, String successMessage,
			String errorMessage) {

		if (data != null) {
			response.setSuccess(true);
			response.setData(data);
			response.setMessage(successMessage);
		}else {
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, errorMessage);
		}
		return response;
	}
	
	/**
	 * Use this format when the API returns only boolean
	 * 
	 * @param response
	 * @param flag
	 * @param successMessage
	 * @param errorMessage
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static EatcoResponse createResponseForFlags(EatcoResponse response, boolean flag, String successMessage,
			String errorMessage) {
		if (flag) {
			response.setSuccess(flag);
			response.setData(flag);
			response.setMessage(successMessage);
		} else {
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, errorMessage);
		}
		return response;
	}
}
