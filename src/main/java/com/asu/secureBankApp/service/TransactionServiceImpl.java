package com.asu.secureBankApp.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Config.Constants;
import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.TransactionRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.TransactionDAO;
import com.asu.secureBankApp.dao.UserDAO;
import com.asu.secureBankApp.util.Util;

import constants.ErrorCodes;
import constants.RoleType;
import constants.TransactionStatus;
import constants.TransactionType;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Transactional
	public StatusResponse transfer(@Valid TransferRequest transferReq, Authentication auth, boolean isApproved) {
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);

		AccountDAO fromAccount = accountRepository.findById(transferReq.getFromAccNo()).orElse(null);
		AccountDAO toAccount = accountRepository.findById(transferReq.getToAccNo()).orElse(null);

		if (fromAccount == null || toAccount == null) {
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}
		if(fromAccount.getBalance() - transferReq.getTransferAmount() < 0) {
			response.setMsg(ErrorCodes.INSUFFICIENT_FUNDS);
			return response;
		}
		RoleType accRoleType = fromAccount.getUser().getAuthRole().getRoleType();
		System.out.println("auth.getPrincipal()" + auth.getName());
		UserDAO user = userRepository.findByUsername(auth.getPrincipal().toString());
		RoleType authRoleType = user.getAuthRole()
				.getRoleType();
		
		// Can't update someone else's account if authUser is not employee
		if (authRoleType != accRoleType && !Util.isEmployee(authRoleType)) {
			response.setMsg(ErrorCodes.INVALID_ACCESS);
			return response;
		}
		UpdateBalanceRequest fromUpdateBalanceRequest = new UpdateBalanceRequest();
		UpdateBalanceRequest toUpdateBalanceRequest = new UpdateBalanceRequest();
		fromUpdateBalanceRequest.setAccountNo(fromAccount.getId());
		fromUpdateBalanceRequest.setAmount(-transferReq.getTransferAmount());
		
		toUpdateBalanceRequest.setAccountNo(toAccount.getId());
		toUpdateBalanceRequest.setAmount(transferReq.getTransferAmount());
		
		TransactionDAO transactionDAO = new TransactionDAO();
		
		float transactionSum = transactionRepository.dailyTransactionSum(user);
		boolean approvalRequired = !Util.isEmployee(authRoleType)
				&& (transactionSum + transferReq.getTransferAmount()) > Constants.TRANSFER_CRITICAL_LIMIT;
				
		if (approvalRequired) {
			transactionDAO.setCreatedBy(user);
			transactionDAO.setFromAccount(fromAccount);
			transactionDAO.setToAccount(toAccount);
			transactionDAO.setTransactionAmount(Math.abs(transferReq.getTransferAmount()));
			transactionDAO.setType(TransactionType.TRANSFER);
			transactionDAO.setStatus(TransactionStatus.PENDING);
			transactionDAO.setTransactionTimestamp(Calendar.getInstance().getTime());
			transactionRepository.save(transactionDAO);
			response.setIsSuccess(true);
			response.setMsg(ErrorCodes.SUBMIT_APPROVAL);
		} else {
			if(doUpdateBalance(fromUpdateBalanceRequest) && doUpdateBalance(toUpdateBalanceRequest)) {
				// Don't create new Transaction record if already approved. Update existing one
				if(isApproved) {
					response.setIsSuccess(true);
					return response;
				}
				// Executed if approval not required	
				transactionDAO.setCreatedBy(user);
				transactionDAO.setFromAccount(fromAccount);
				transactionDAO.setToAccount(toAccount);
				transactionDAO.setTransactionAmount(Math.abs(transferReq.getTransferAmount()));
				transactionDAO.setType(TransactionType.TRANSFER);
				transactionDAO.setStatus(TransactionStatus.COMPLETED);
				response = submitTransactionRequest(transactionDAO);
				if(response.getIsSuccess())
					response.setMsg(ErrorCodes.SUCCESS);				
			} else {
				throw new RuntimeException();
			}
		}
		return response;
	}

	@Override
	@Transactional
	public StatusResponse updateBalance(@Valid UpdateBalanceRequest updateBalanceRequest, Authentication auth, boolean isTransfer) {
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);
		AccountDAO account = accountRepository.findById(updateBalanceRequest.getAccountNo()).orElse(null);
		if (account == null) {
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}
		if(account.getBalance() + updateBalanceRequest.getAmount() < 0) {
			response.setMsg(ErrorCodes.INSUFFICIENT_FUNDS);
			return response;
		}
		RoleType accRoleType = account.getUser().getAuthRole().getRoleType();
		System.out.println("auth.getPrincipal()" + auth.getName());
		UserDAO user = userRepository.findByUsername(auth.getPrincipal().toString());
		System.out.println("user.getAuthRole(): " + user.getAuthRole());
		RoleType authRoleType = user.getAuthRole()
				.getRoleType();
		
		// Can't update someone else's account if authUser is not employee. Ignore condition if it is called from transfer function as it is internal
		if (!isTransfer && authRoleType != accRoleType && !Util.isEmployee(authRoleType)) {
			response.setMsg(ErrorCodes.INVALID_ACCESS);
			return response;
		}
		float transactionSum = transactionRepository.dailyTransactionSum(user);
		boolean approvalRequired = !Util.isEmployee(authRoleType)
				&& (transactionSum + updateBalanceRequest.getAmount()) > Constants.UPDATE_BALANCE_CRITICAL_LIMIT;
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.setCreatedBy(user);
		transactionDAO.setFromAccount(account);
		transactionDAO.setTransactionAmount(Math.abs(updateBalanceRequest.getAmount()));
		transactionDAO.setType((updateBalanceRequest.getAmount()>0)?TransactionType.CREDIT : TransactionType.DEBIT);
		if (approvalRequired) {
			transactionDAO.setStatus(TransactionStatus.PENDING);
			transactionDAO.setTransactionTimestamp(Calendar.getInstance().getTime());
			transactionRepository.save(transactionDAO);
			response.setIsSuccess(true);
			response.setMsg(ErrorCodes.SUBMIT_APPROVAL);
		} else {
			if(doUpdateBalance(updateBalanceRequest)) {
				transactionDAO.setStatus(TransactionStatus.COMPLETED);
				response = submitTransactionRequest(transactionDAO);
				if(response.getIsSuccess())
					response.setMsg(ErrorCodes.SUCCESS);			
			} else {
				throw new RuntimeException();
			}
		}
		return response;
	}

	@Override
	@Transactional
	public boolean doUpdateBalance(@Valid UpdateBalanceRequest updateBalanceRequest) {
		AccountDAO account = accountRepository.findById(updateBalanceRequest.getAccountNo()).orElse(null);
		if (account == null) {
			System.out.println(updateBalanceRequest.getAccountNo() + " does not exist");
			return false;
		}
		double bal = account.getBalance();
		bal += updateBalanceRequest.getAmount();
		account.setBalance(bal);
		accountRepository.save(account);
		return true;
	}
	
	/*
	 * Only called when a Transaction is TransactionStatus.CONFIRMED or APPROVED 
	 */
	@Override
	@Transactional
	public StatusResponse submitTransactionRequest(@Valid TransactionDAO transactionDAO) {
		transactionDAO.setTransactionTimestamp(Calendar.getInstance().getTime());
		transactionRepository.save(transactionDAO);
		// Insert to Hyperledger
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(true);
		return response;
	}

	@Override
	public StatusResponse approveTransaction(String transactionId, Authentication auth) {
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);
		UserDAO user = userRepository.findByUsername(auth.getPrincipal().toString());
		System.out.println("user.getAuthRole(): " + user.getAuthRole());
		RoleType authRoleType = user.getAuthRole()
				.getRoleType();
		if(!Util.isEmployee(authRoleType)) {
			response.setMsg(ErrorCodes.INVALID_ACCESS);
			return response; 
		}
		System.out.println(Integer.parseInt(transactionId));
		TransactionDAO transaction = transactionRepository.findByTransactionId(Integer.parseInt(transactionId));
		transaction.setApprovedAt(Calendar.getInstance().getTime());
		transaction.setApprovedBy(user);
		transaction.setStatus(TransactionStatus.APPROVED);
		transactionRepository.save(transaction);
		if(transaction.getType() == TransactionType.CREDIT || transaction.getType() == TransactionType.DEBIT) {
			UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest();
			updateBalanceRequest.setAccountNo(transaction.getFromAccount().getId());
			updateBalanceRequest.setAmount(transaction.getTransactionAmount());
			if(doUpdateBalance(updateBalanceRequest)) {
				response.setIsSuccess(true);
				response.setMsg(ErrorCodes.SUCCESS);
			} else {
				throw new RuntimeException();
			}
		} else {  // TRANSFER - Create request and call transfer function internally with isApproved=true
			TransferRequest transferReq = new TransferRequest();
			transferReq.setFromAccNo(transaction.getFromAccount().getId());
			transferReq.setToAccNo(transaction.getToAccount().getId());
			transferReq.setTransferAmount(transaction.getTransactionAmount());
			response = transfer(transferReq, auth, true); 
			if(response.getIsSuccess()) {
				response.setIsSuccess(true);
				response.setMsg(ErrorCodes.SUCCESS);
			} else {
				throw new RuntimeException();
			}
		}
		return response;
	}

	@Override
	@Transactional
	public StatusResponse rejectTransaction(String transactionId, Authentication auth) {
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);
		TransactionDAO transaction = transactionRepository.findByTransactionId(Integer.parseInt(transactionId));
		UserDAO user = userRepository.findByUsername(auth.getPrincipal().toString());
		if(transaction == null) {
			response.setMsg(ErrorCodes.ID_NOT_FOUND);
			return response;
		}
		transaction.setStatus(TransactionStatus.DECLINED);
		transaction.setApprovedAt(Calendar.getInstance().getTime());
		transaction.setApprovedBy(user);
		transactionRepository.save(transaction);
		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);
		return response;
	}

	@Override
	public List<TransactionDAO> getTransaction(Integer type, Integer status, String userName, Authentication auth) throws Exception {
		TransactionType tType = null;
		if(type != null) {
			switch(type) {
			case 1:
				tType = TransactionType.CREDIT;
				break;
			case 2:
				tType = TransactionType.DEBIT;
				break;
			case 3:
				tType = TransactionType.TRANSFER;
				break;
			default:
				throw new Exception("Type should be between 1,3");				
			}
		}
		TransactionStatus tStatus = null;
		if(status != null) {
			switch(status) {
			case 1:
				tStatus = TransactionStatus.APPROVED;
				break;
			case 2:
				tStatus = TransactionStatus.DECLINED;
				break;
			case 3:
				tStatus = TransactionStatus.PENDING;
				break;
			default:
				throw new Exception("Status should be between 1,3");
			}		
		}
		UserDAO authUser = userRepository.findByUsername(auth.getPrincipal().toString()), user = null;
		// For Technical Account Access where authUser != user
		if(userName != null) {
			user = userRepository.findByUsername(userName);
			if(user == null)
				throw new Exception(ErrorCodes.ID_NOT_FOUND);			
		}
		// Can't access someone else's account if authUser is not employee
		if(user != null && !Util.isEmployee(authUser.getAuthRole().getRoleType()))
			throw new Exception(ErrorCodes.INVALID_ACCESS);
		
		// Under TAC for someone, can't see PENDING,APPROVED or COMPLETED transactions. Call without status
		if(tStatus != null && user != null && Util.isEmployee(user.getAuthRole().getRoleType()))
			throw new Exception(ErrorCodes.INVALID_ACCESS);
		
		// All validations done. Use only user variable from here
		if(user == null)
			user = authUser;
		List<TransactionStatus> statuses = new ArrayList<>();
		if(tStatus == null) {
			statuses.add(TransactionStatus.APPROVED);
			statuses.add(TransactionStatus.COMPLETED);
		} else {
			statuses.add(tStatus);
		}
		List<TransactionDAO> responses= null;
		if(tStatus == TransactionStatus.APPROVED || tStatus == TransactionStatus.DECLINED)
			responses = transactionRepository.findByApprovedByAndStatusIn(user, statuses);
		else if (tStatus == TransactionStatus.PENDING)
			responses = transactionRepository.findByStatusIn(statuses);
		else {
			responses = transactionRepository.findByFromAccount_User(user);
		}
		for(TransactionDAO t : responses) {
			System.out.println("fixing...");
			UserDAO createdBy = t.getCreatedBy(); 
			createdBy.setAccounts(null);
			createdBy.setAuthRole(null);
			t.setCreatedBy(createdBy);
			t.getFromAccount().setUser(null);;
			if(t.getToAccount() != null)
				t.getToAccount().setUser(null);
			if(t.getApprovedBy() != null) {
				t.getApprovedBy().setAuthRole(null);
				t.getApprovedBy().setAccounts(null);
			}
		}
		return responses;
	}

	private List<TransactionDAO> getTransactionsForToday(UserDAO user) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
		return transactionRepository.findByFromAccount_UserAndTransactionTimestampGreaterThan(user, date);
	}

}
