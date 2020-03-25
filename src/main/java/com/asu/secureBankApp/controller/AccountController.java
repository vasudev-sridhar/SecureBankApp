package com.asu.secureBankApp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asu.secureBankApp.Config.Constants;
import com.asu.secureBankApp.Repository.AccountRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.UpdateBalanceRequest;
import com.asu.secureBankApp.Request.UpdateInterestRequest;
import com.asu.secureBankApp.Response.AccountResponses;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.CreateAccountReqDAO;
import com.asu.secureBankApp.dao.Transaction;
import com.asu.secureBankApp.service.AccountService;
import org.springframework.security.core.Authentication;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	AccountService accountService;
	
//	@Autowired
//	UserService userService;
	
	@RequestMapping(value="/list/{page}", method= RequestMethod.GET)
    public HashMap<String, Object> getList(@PathVariable("page") int page, Authentication authentication, @ModelAttribute("message") String message) {
		
		HashMap<String, Object> response = accountService.getListPage(authentication, page);
        response.put("message", message);
        return response;
    }

	@GetMapping(value = "/get/{user_id}")
	public @ResponseBody AccountResponses getAccounts(@PathVariable(value = "user_id") String userId) {
		AccountResponses response = new AccountResponses();
		List<AccountDAO> accounts = accountRepository.findByUserId(Integer.valueOf(userId));
		for(AccountDAO account : accounts) {
			account.setUser(null);
		}
		response.setAccounts(accounts);
		return response;
	}
	
	@PostMapping(value = "/balance", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateBalance(@RequestBody @Valid UpdateBalanceRequest updateBalanceRequest) {
		return accountService.updateBalance(updateBalanceRequest);
	}

	@PostMapping(value = "/createAccount", consumes = { "application/json" })
	public @ResponseBody StatusResponse createNewAccount(@RequestBody @Valid CreateAccountReqDAO createAccountReqDAO) {
		StatusResponse response = accountService.createAccount(createAccountReqDAO);
		return response;
	}
	
    @RequestMapping(value="/delete/{acc_no}", method= RequestMethod.POST)
    public HashMap<String, Object> deleteAccount(@PathVariable("account_no")int acc_no) {
        
    	HashMap<String, Object> response = new HashMap<String, Object>();
    	accountRepository.deleteById(acc_no);
    	response.put("redirect", "/account/list/1");
        return response;
    }
    
    @RequestMapping(value="/deposit", method= RequestMethod.POST)
    public ModelAndView depositPost(@Valid Transaction transaction, BindingResult bindingResult,Authentication authentication,  RedirectAttributes redirectAttributes) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        String name;
        int role;
        ModelAndView modelAndView;
        HashMap<String, Object> response = new HashMap<String, Object>();
        
        Long id = userService.findUserByEmail(authentication.getName());
        name = userService.getEmployeeById(id).getEmployee_name();
        role = Constants.TIER1;
        
        if(roles.contains("TIER1")){
        	response.put("redirect", "redirect:/account/deposit");
//            modelAndView = new ModelAndView("redirect:/account/deposit");
        }else{
        	response.put("redirect", "redirect:/account/deposit1");
//            modelAndView = new ModelAndView("redirect:/account/deposit1");
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message","Please fix the errors");
            return modelAndView;
        }
        String message = depositandwithdraw(Config.CREDIT, transaction,name, role, authentication);
        if(message.contains("Success")){
            logService.saveLog(authentication.getName(), "Deposited money for account "+transaction.getAccount_no()+" $"+transaction.getTransaction_amount());
        }
        redirectAttributes.addFlashAttribute("message", message);
        return modelAndView;
    }
    

	@PatchMapping(value = "/updateInterest", consumes = { "application/json" })
	public @ResponseBody StatusResponse updateInterest(
			@RequestBody @Valid UpdateInterestRequest updateInterestRequest) {
		return accountService.updateInterest(updateInterestRequest);
	}
}
