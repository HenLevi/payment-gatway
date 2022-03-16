package com.springboot.services;

import java.util.Base64;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.springboot.dao.TransactionDetailsDao;
import com.springboot.model.TransactionDetails;

@Service
public class TransactionDetailsService {

	private static Logger LOG = LoggerFactory.getLogger(TransactionDetailsService.class);

	@Autowired
	TransactionDetailsDao transactiondetailsDao;

	public List<TransactionDetails> getTransactional() {
		try {
			List<TransactionDetails> allTransactional = transactiondetailsDao.findAll();
			return allTransactional;
		} catch (Exception e) {
			LOG.error("Error in service: TransactionDetailsService in getTransactional function, exception:" + e);
			return null;
		}
	}

	// Base64 decoder

	public TransactionDetails getTransactionDetailsDecrypt(TransactionDetails transactiondetails) {

		try {

			// Cardholder name
			String decodedStringName = transactiondetails.getCardHolder().getName();
			byte[] decodedBytes = Base64.getDecoder().decode(decodedStringName);
			String decodedString = new String(decodedBytes);

			transactiondetails.getCardHolder().setName(decodedString);

			// PAN
			String pan = transactiondetails.getCard().getPan();
			String lastFourDigitDecode = pan.substring(pan.length() - 4); // last 4 digits
			String eightFirstsDigitDecode = pan.substring(0, 8); // first 8 digits

			byte[] decodedBytesPan = Base64.getDecoder().decode(eightFirstsDigitDecode);
			String decodedStringPan = new String(decodedBytesPan);
			String newDecptPan = decodedStringPan.concat(lastFourDigitDecode); // concat encrypt with 4 last digit

			transactiondetails.getCard().setPan(newDecptPan);

			// Expiry date

			String decodedExpiry = transactiondetails.getCard().getExpiry();
			byte[] decodedBytesExpiry = Base64.getDecoder().decode(decodedExpiry);
			String decodedExpiryString = new String(decodedBytesExpiry);

			transactiondetails.getCard().setExpiry(decodedExpiryString);

			// Cvv

			return transactiondetails;

		} catch (Exception e) {
			LOG.error("Error in service: TransactionDetailsService in getTransactionDetailsById function, exception:"
					+ e);
		}
		return transactiondetails;
	}

	public TransactionDetails getTransactionDetailsById(@PathVariable(name = "id") long id) {
		try {
			List<TransactionDetails> allTransactionDetails = transactiondetailsDao.findAll();
			TransactionDetails transactionDetails = null;

			transactionDetails = allTransactionDetails.stream().filter(transactionDetailsFilter -> String.valueOf(id)
					.equals(String.valueOf(transactionDetailsFilter.getId()))).findFirst().orElse(null);

			transactionDetails = getTransactionDetailsDecrypt(transactionDetails);

			return transactionDetails;
		} catch (Exception e) {
			LOG.error("Error in service: TransactionDetailsService in getTransactionDetailsById function, exception:"
					+ e);
		}

		return null;

	}

	// Base64 encoder
	public TransactionDetails getTransactionDetailsEncrypt(TransactionDetails transactiondetails) {

		try {
			// Cardholder Name
			String name = transactiondetails.getCardHolder().getName();
			String encodedStringName = Base64.getEncoder().encodeToString(name.getBytes());
			transactiondetails.getCardHolder().setName(encodedStringName);

			// PAN
			String pan = transactiondetails.getCard().getPan();
			String lastFourDigit = pan.substring(pan.length() - 4); // last 4 digits
			String eightFirstsDigit = pan.substring(0, 8); // first 8 digits
			String encriptfirstsDigit = Base64.getEncoder().encodeToString(eightFirstsDigit.getBytes());
			String newEncptPan = encriptfirstsDigit.concat(lastFourDigit); // concat encrypt with 4 last digit
			transactiondetails.getCard().setPan(newEncptPan);

			// Expiry Date
			String expiryDate = transactiondetails.getCard().getExpiry();
			String encodeExpiryDate = Base64.getEncoder().encodeToString(expiryDate.getBytes());
			transactiondetails.getCard().setExpiry(encodeExpiryDate);

			// Cvv: My decision:display 0.
			String cvv = "0";
			transactiondetails.getCard().setCvv(cvv);

		} catch (Exception e) {
			LOG.error("Error in service: TransactionDetailsService in getTransactionDetailsEncrypt function, exception:"
					+ e);
		}

		return transactiondetails;
	}

	public TransactionDetails saveNewTransactionDetails(TransactionDetails transactiondetails) {

		try {
			transactiondetails = getTransactionDetailsEncrypt(transactiondetails);
		} catch (Exception e) {
			LOG.error("Error in service: TransactionDetailsService in getTransactionDetailsEncrypt function, exception:"
					+ e);
		}

		return transactiondetailsDao.save(transactiondetails);

	}

}
