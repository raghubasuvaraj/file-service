package com.eatco.fileservice.response.formate;

import lombok.Data;

/**
 * Common response model for the all API
 * 
 * @author Arya C Achari
 * @since 0.0.1
 *
 * @param <T>
 */
@Data
public class EatcoResponse<T> {

	/**
	 * Generic data which will adopt to the different resources
	 */
	private T data;

	/**
	 * The success/error message of the API requested.
	 */
	private String message;

	/**
	 * The parameter which indicates the status of API response.
	 */
	private boolean success;

	/**
	 * The application specific error codes.
	 */
	private String errorCode;

	/**
	 * The hateoas resource link
	 */
	private String path;

}
