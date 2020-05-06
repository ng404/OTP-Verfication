package com.otpTimer.timer;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.otpTimer.Controller.OTPController;

public class TaskHappen extends TimerTask {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	String Email;
	
	

	public String getEmail() {
		return Email;
	}



	public void setEmail(String email) {
		Email = email;
	}



	@Override
	public void run() {
		//System.out.println("value get by user"+id);
       OTPController x=new OTPController();
       x.deleteOtp(Email);

		
	}
	

}
