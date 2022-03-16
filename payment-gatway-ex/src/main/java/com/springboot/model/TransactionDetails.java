package com.springboot.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {

	@Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "invoice", nullable = false)
	@NotEmpty(message = "Invoice is required field")
	@JsonProperty("invoice")
	private String invoice;

	@Column(name = "amount", nullable = false)
	@NotNull(message = "Amount is required field")
	@JsonProperty("amount")
	private int amount;

	@Column(name = "currency", nullable = false)
	@NotEmpty(message = "Currency is required field")
	@JsonProperty("currency")
	private String currency;

	@Valid
	@JsonProperty("cardholder")
	@Embedded
	private CardHolder cardHolder;

	@Valid
	@JsonProperty("card")
	@Embedded
	private Card card;

}
