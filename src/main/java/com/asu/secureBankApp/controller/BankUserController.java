package com.asu.secureBankApp.controller;

import com.asu.secureBankApp.dao.AccountDAO;
import com.asu.secureBankApp.dao.UserDAO;
import com.asu.secureBankApp.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
public class BankUserController {

    @Autowired
    BankUserService bankUserService;

    @RequestMapping(value = "/accounts/email/{emailId}", method= RequestMethod.GET)
    public List<AccountDAO> getAccountsByEmail(@PathVariable String emailId) {
        return bankUserService.getAccountsByEmail(emailId);
    }

    @RequestMapping(value = "/accounts/contact/{phoneNo}", method= RequestMethod.GET)
    public List<AccountDAO> getAccountsByContact(@PathVariable String phoneNo){
        return bankUserService.getAccountsByContact(phoneNo);
    }

    @RequestMapping(value="/edit", method= RequestMethod.POST)
    public HashMap editAccount(@Valid UserDAO user, BindingResult bindingResult, Authentication authentication, RedirectAttributes redirectAttributes) {
        HashMap<String, Object> responseMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            responseMap.put("redirect", "redirect:/user");
            redirectAttributes.addFlashAttribute("message","Please fix the errors");
            //return new ModelAndView("redirect:/user");
            return responseMap;
        }
        int id =  userService.findUserByEmail(authentication.getName());
        User old_user = userService.getUserByUserId(id);
//        old_user.setAddress(user.getAddress());
//        old_user.setContact(user.getContact());
//        old_user.setEmailId(user.getEmailId());
//        old_user.setDob(user.getDob());
        if(!user.getEmailId().equals(old_user.getEmailId()) || !user.getName().equals(old_user.getName())){
            redirectAttributes.addFlashAttribute("message","Cannot change name or email!");
            return new ModelAndView("redirect:/user");
        }
        if(user.getContact() == null || !user.getContact().matches("-?\\d+(\\.\\d+)?") || user.getContact().length() != 10){
            redirectAttributes.addFlashAttribute("message","Contact Number not valid!");
            return new ModelAndView("redirect:/user");
        }
        Date today = new Date();
        if(user.getDob().after(today)){
            redirectAttributes.addFlashAttribute("message","Date of Birth Cannot be greater than today!");
            return new ModelAndView("redirect:/user");
        }
        try{
            User user1 = userService.getUserByUserId(userService.findUserByPhone(user.getContact()));
            if( user1 != null && !user1.getUserId().equals(old_user.getUserId())){
                redirectAttributes.addFlashAttribute("message","Contact Number already exists!");
                return new ModelAndView("redirect:/user");
            }
        }
        catch (Exception e){
            System.out.println("Exception");
        }
//        userService.saveOrUpdate(old_user);
        AccountRequest accountRequest = new AccountRequest();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user_id",old_user.getUserId());
        attributes.put("name", old_user.getName());
        attributes.put("gender", old_user.getGender());
        attributes.put("dob", user.getDob());
        attributes.put("contact", user.getContact());
        attributes.put("email_id",old_user.getEmailId());
        attributes.put("address", user.getAddress());
        accountRequest.setDescription("Profile Edit for "+ user.getName() +" : "+user.getEmailId() );
        accountRequest.setUser(attributes);
        accountRequest.setCreated_by(old_user.getName());
        accountRequest.setStatus_id(Config.PENDING);
        accountRequest.setCreated_at(new Timestamp(System.currentTimeMillis()));
        accountRequest.setType(Config.USER_TYPE);
        accountRequest.setRole(Config.TIER1);
        try {
            accountRequest.serializeuser();
        }
        catch(Exception e){
            System.out.println("Exception");
        }
        accountRequestService.saveOrUpdate(accountRequest);

        redirectAttributes.addFlashAttribute("message","Successfully saved data! Pending approval with authorities!");
        logService.saveLog(authentication.getName(),"Changed User profile for "+old_user.getEmailId());
        return new ModelAndView("redirect:/user");
    }

}
