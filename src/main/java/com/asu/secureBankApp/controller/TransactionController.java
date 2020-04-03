package com.asu.secureBankApp.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.TransactionDAO;
import com.asu.secureBankApp.service.TransactionService;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	ServletContext context;

	@PostMapping(value = "/transfer")
	public StatusResponse transfer(@RequestBody @Valid TransferRequest transferReq, Authentication auth) {
		return transactionService.transfer(transferReq, auth, false);
	}

	@PostMapping(value = "/balance")
	public @ResponseBody StatusResponse updateBalance(@RequestBody @Valid UpdateBalanceRequest updateBalanceRequest,
			Authentication auth) throws Exception {
		return transactionService.updateBalance(updateBalanceRequest, auth, false);
	}

	@PostMapping(value = "/approve/{transaction_id}")
	public @ResponseBody StatusResponse approveTransaction(@PathVariable(value = "transaction_id") String transactionId,
			Authentication auth) throws Exception {
		return transactionService.approveTransaction(transactionId, auth);
	}

	@PostMapping(value = "/reject/{transaction_id}")
	public @ResponseBody StatusResponse rejectTransaction(@PathVariable(value = "transaction_id") String transactionId,
			Authentication auth) {
		return transactionService.rejectTransaction(transactionId, auth);
	}

	@GetMapping(value = "/get")
	public @ResponseBody List<TransactionDAO> getTransaction(@RequestParam(required = false) Integer type,
			@RequestParam(required = false) Integer status, @RequestParam(required = false) String userName,
			@RequestParam(required = false, defaultValue = "false") Boolean isCritical, Authentication auth) throws Exception {
		return transactionService.getTransaction(type, status, userName, isCritical, auth);
	}

	@RequestMapping(value = "/statement", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadStatement(@RequestParam(required = false) String userName,
			Authentication auth) throws Exception {
		return transactionService.downloadStatement(userName, auth);
	}
}
