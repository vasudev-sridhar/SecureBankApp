package com.asu.secureBankApp.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asu.secureBankApp.Repository.AppointmentRepository;
import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.Request.AppointmentRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AppointmentDAO;
import com.asu.secureBankApp.dao.UserDAO;

import constants.ErrorCodes;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	SystemLoggerService logger;

	public StatusResponse createAppointment(AppointmentRequest appointmentReq) {
		
		StatusResponse response = new StatusResponse();
		response.setIsSuccess(false);		
		UserDAO user = userRepository.findById(appointmentReq.getUserid()).orElseGet(null);
		AppointmentDAO appointmentDAO = new AppointmentDAO(); 
		appointmentDAO.setUser(user);
		appointmentDAO.setApp_date(appointmentReq.getDate());
		appointmentDAO.setApp_time(appointmentReq.getTime());
		appointmentRepository.save(appointmentDAO);
		logger.log(appointmentReq.getUserid(), "User has scheduled an appointment", "APPOINTMENT_SCHEDULED");
		response.setIsSuccess(true);
		response.setMsg(ErrorCodes.SUCCESS);	
		return response;
	}

}
