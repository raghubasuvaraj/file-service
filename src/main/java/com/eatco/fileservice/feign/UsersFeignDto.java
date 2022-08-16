package com.eatco.fileservice.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Users details from the USER-SERVICE.
 * 
 * @author Arya C Achari
 * @since 1.0.0
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersFeignDto {
	
	private String id;
	private String userName;
	private String email;
	private String phoneNumber;
	private String password;
	
}
