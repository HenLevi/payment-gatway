package com.springboot.model;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CardHolder {

	@Column(name = "name")
	@NotEmpty(message = "Name is required field")
	@JsonProperty("name")
	private String name;

	@Column(name = "email")
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	@JsonProperty("email")
	private String email;

}
