package com.asu.secureBankApp.Repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.asu.secureBankApp.dao.AppointmentDAO;
import com.asu.secureBankApp.dao.UserDAO;

@Repository
public interface AppointmentRepository extends CrudRepository<AppointmentDAO, Integer>  {
	//AppointmentDAO createAppointment(UserDAO user, Date appDate, String appTime);

}
