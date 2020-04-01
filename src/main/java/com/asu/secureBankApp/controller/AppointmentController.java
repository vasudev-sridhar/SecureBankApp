package com.asu.secureBankApp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Request.AppointmentRequest;
import com.asu.secureBankApp.Request.TransferRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AppointmentDAO;
import com.asu.secureBankApp.service.AppointmentService;



@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping(value = "/create")
	public StatusResponse createAppointment(@RequestBody @Valid AppointmentRequest app) {
		return appointmentService.createAppointment(app);
	}
	
}
