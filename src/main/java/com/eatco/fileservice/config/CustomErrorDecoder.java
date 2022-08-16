package com.eatco.fileservice.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.exceptions.FUException;
import com.eatco.fileservice.response.formate.EatcoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

	@Autowired
	private ObjectMapper objectMapper;

	public Exception decode(String methodKey, Response response) {
		try {
			InputStream content = response.body().asInputStream();
			int status = response.status();
			if (status != -1 && content != null) {
				EatcoResponse errorResponse = objectMapper.readValue(content, EatcoResponse.class);
				if (status == HttpStatus.NOT_FOUND.value()) {
					throw new FUException(ErrorCode.NOT_FOUND, errorResponse.getMessage());
				} else if (status == HttpStatus.FORBIDDEN.value()) {
					throw new FUException(ErrorCode.ACCESS_DENIED, errorResponse.getMessage());
				} else if (status == HttpStatus.UNAUTHORIZED.value()) {
					throw new FUException(ErrorCode.UNAUTHORIZED, errorResponse.getMessage());
				} else if (status == HttpStatus.BAD_REQUEST.value()) {
					throw new FUException(ErrorCode.BAD_REQUEST, errorResponse.getMessage());
				} else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, errorResponse.getMessage());
				} else if (status == HttpStatus.CONFLICT.value()) {
					throw new FUException(ErrorCode.CONFLICT, errorResponse.getMessage());
				} else {
					throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, errorResponse.getMessage());
				}
			}
		} catch (IOException ioe) {
			log.error(ExceptionUtils.getStackTrace(ioe));
		}
		throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("internal.server.error", null));
	}
}