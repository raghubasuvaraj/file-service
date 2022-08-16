package com.eatco.fileservice.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {
	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void apply(RequestTemplate template) {

		ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());

		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			String userToken = request.getHeader(AUTHORIZATION_HEADER);
			template.header(AUTHORIZATION_HEADER, userToken);
		}
	}
}
