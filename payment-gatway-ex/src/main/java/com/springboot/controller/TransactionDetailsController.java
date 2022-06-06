package com.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.model.TransactionDetails;
import com.springboot.services.TransactionDetailsService;

@RestController
@RequestMapping(value = "/api/transactionDetails")
public class TransactionDetailsController {

	private static Logger LOG = LoggerFactory.getLogger(TransactionDetailsController.class);

	@Autowired
	TransactionDetailsService transactiondetailsservice;

	@GetMapping(value = "/getTransactionals", produces = "application/json")
	public ResponseEntity<List<TransactionDetails>> getTransactional() {
		try {
			List<TransactionDetails> allTransactional = transactiondetailsservice.getTransactional();
			if (allTransactional == null) {
				LOG.error("List of all transactional is empty");
			}
			return new ResponseEntity<List<TransactionDetails>>(allTransactional, HttpStatus.FOUND);
		} catch (Exception e) {
			LOG.error("Error in controller: TransactionDetailsController in getTransactional function, exception:" + e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping(value = "/getTransactional/{id}", produces = "application/json")
	public ResponseEntity<TransactionDetails> getTransactionalById(@PathVariable(name = "id") long id) {
		try {
			TransactionDetails transactionaldetail = transactiondetailsservice.getTransactionDetailsById(id);

			if (transactionaldetail == null) {
				LOG.error("transactionaldetail is null");
			}
			return new ResponseEntity<TransactionDetails>(transactionaldetail, HttpStatus.FOUND);
		} catch (Exception e) {
			LOG.error("Error in controller: TransactionDetailsController in getTransactionalById function, exception:"
					+ e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/addTransactionDetailsEncription", produces = "application/json", consumes = "application/json")
	public ResponseEntity<TransactionDetails> CreateTransactionDetails(
			@Valid @RequestBody TransactionDetails transactiondetails) {
		try {
			@SuppressWarnings("unused")
			TransactionDetails savedTransactionDetailsAfterEncoder = transactiondetailsservice
					.saveNewTransactionDetails(transactiondetails);
			return new ResponseEntity<TransactionDetails>(HttpStatus.CREATED);
		} catch (Exception e) {
			LOG.error(
					"Error in controller: TransactionDetailsController in CreateTransactionDetails function, exception:"
							+ e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
