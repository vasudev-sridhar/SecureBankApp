package com.asu.secureBankApp.service;

import com.asu.secureBankApp.Request.AppointmentRequest;
import com.asu.secureBankApp.Response.StatusResponse;
import com.asu.secureBankApp.dao.AppointmentDAO;

public interface AppointmentService {
	StatusResponse createAppointment(AppointmentRequest appointmentReq);

}
