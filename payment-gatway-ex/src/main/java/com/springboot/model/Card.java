package com.springboot.model;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Card {

	@Column(name = "pan")
	@NotEmpty
	@Size(min = 16, max = 16, message = " Pan is 16 digit")
	@JsonProperty("pan")
	private String pan;

	@Column(name = "expiry")
	@NotEmpty
	@Size(min = 4)
	@JsonProperty("expiry")
	private String expiry;

	@Column(name = "cvv")
	@NotEmpty(message = "Cvv is required field")
	@JsonProperty("cvv")
	//transient String cvv;
	private String cvv;
}
