package com.eatco.fileservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eatco.fileservice.component.Translator;
import com.eatco.fileservice.enums.ErrorCode;
import com.eatco.fileservice.exceptions.FUException;
import com.eatco.fileservice.feign.UsersFeignClient;
import com.eatco.fileservice.feign.UsersFeignDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author R@aghu The {@code AuthServiceImpl} implements
 *         {@code UserDetailsService} <br>
 *         {@code @Service} is a stereotypical annotation used for Service Layer
 *         <br>
 *         {@code Slf4j} is a Logger annotation obtained from Lombok dependency
 *         for logging the requests and responses
 */

@Slf4j
@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements UserDetailsService {

	private final UsersFeignClient usersClient;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UsersFeignDto usersDto = getUsersDetails(userName);
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		return new User(usersDto.getUserName(), usersDto.getPassword(), authorities);
	}

	/**
	 * Users details get from the users-service by the feign client call.
	 * 
	 * @param userName
	 * @return
	 */
	private UsersFeignDto getUsersDetails(String userName) {
		if (StringUtils.isEmpty(userName)) {
			throw new FUException(ErrorCode.BAD_REQUEST, Translator.toLocale("user.name.mandatory", null));
		}
		try {
			// feign call
			return usersClient.getUsersByUsernameForFeign(userName).getData();
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new FUException(ErrorCode.INTERNAL_SERVER_ERROR, Translator.toLocale("user.not.found", null));
		}
	}
}
