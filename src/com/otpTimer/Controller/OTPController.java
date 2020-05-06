package com.otpTimer.Controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otpTimer.timer.TaskHappen;



@Controller
public class OTPController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="generateOTP")
	public String generateOTP(HttpServletRequest req)
	{
		 Random rand=new Random();
	     int x=10000+rand.nextInt(90000);
			    System.out.println(x);
			
       String email=req.getParameter("email");
       
				final String procedureCall = "{call otp(?,?,?)}";
				Connection connection = null;
				try {	
				connection = jdbcTemplate.getDataSource().getConnection();
				CallableStatement callableSt = connection.prepareCall(procedureCall);
				callableSt.setString(1, "generateOtp");
				callableSt.setString(2, email);
				callableSt.setInt(3, x);
				
				
			    callableSt.execute();
			    
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
				SendMail sm=new SendMail();
				String body="Hello,"+email+".<br>Your eMail was provided for generating OTP.<br>So your otp is genereated as :- "+x+"This OTP is One Time password and it will be valid for only 5 minutes ";      
		        sm.sendMail(body,email,"Email Confirmation"); 
		        
				Calendar now = Calendar.getInstance();// Calendar class is used to get a calendar using the current time zone 
				                                      //and locale of the system.
				System.out.println("hello"+now.get(Calendar.YEAR)+now);
				
				now.add(Calendar.MINUTE, 5);//It is used to add or subtract the specified amount of time to the given
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			
				System.out.println(df.format(now.getTime()));//final time want to delete
				System.out.println(java.time.LocalTime.now());  //present time
				 Timer timer = new Timer();
				 TimerTask task = new TaskHappen();
				 ((TaskHappen) task).setEmail(email);
				 timer.schedule(task,now.getTime());
		return "otp";
	}
	@RequestMapping(value="checkOTP")
	public String checkOTP(HttpServletRequest req)
	{
			int flag=0;
			int otp=Integer.parseInt(req.getParameter("otp"));
       String email=req.getParameter("email");
      
				final String procedureCall = "{call otp(?,?,?)}";
				Connection connection = null;
				try {	
				connection = jdbcTemplate.getDataSource().getConnection();
				CallableStatement callableSt = connection.prepareCall(procedureCall);
				callableSt.setString(1, "checkOtp");
				callableSt.setString(2, email);
				callableSt.setInt(3, 0);
				
				
			    ResultSet rs=callableSt.executeQuery();
			    if(rs.next()){
			    	if(otp==rs.getInt("otp")){
			    		flag=1;
			    		OTPController otpController=new OTPController();
			    		otpController.deleteOtp(email);
			    	}
			    	else{
			    		flag=2;
			    	}
			    }
			    else{
			    	flag=0;
			    }
			    
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			
			switch(flag){
			case 0:
				req.setAttribute("error","Id is not valid");
				return "otp";
			case 2:
				req.setAttribute("error1","OTP is not valid");
				return "otp";
			case 1:
				req.setAttribute("msg","OTP is  valid");
				return "otp";
			default:
				return "otp";
			}
	}
	public void deleteOtp(String email)
	{
		
		String sql;
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/otptimer","root","Niteshgarg@1312");   
			Statement stmt=con.createStatement();
			sql="update otp  set otp=000 where Email_Id='"+email+"';";
			stmt.executeUpdate(sql);
			} 
		catch (Exception e) {
		System.out.println("hello");
		e.printStackTrace();
	}
		System.out.println("Done For Id"+email);
	}

}
