package com.revature.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials { // DTO Data Transfer Object - represents the only data we need for our API to process
						   // Angular will have a form that sends a Credentials object - Martin Fowler
	
	@NotNull
	@NotBlank
	@Length(min=2)
	private String username;
	
	@NotNull
	@NotBlank
	private String password;
	
}
