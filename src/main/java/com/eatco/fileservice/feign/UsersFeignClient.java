package com.eatco.fileservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eatco.fileservice.config.FiegnConfiguration;
import com.eatco.fileservice.response.formate.EatcoResponse;

/**
 * Call the API's from the service USERS-SERVICE, This interface only designed for the USERS-SERVICE.
 * 
 * @author Arya C Achari
 * @since 1.0.0
 *
 */
@FeignClient(name = "USER-SERVICE", configuration = FiegnConfiguration.class)
public interface UsersFeignClient {

	@GetMapping("/v1/api/user/users/username/{username}")
	public EatcoResponse<UsersFeignDto> getUsersByUsernameForFeign(@PathVariable("username") String username);
}
