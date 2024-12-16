package com.smart.controller;

import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
	
	@Autowired
	private UserRepository  userRepository;
    
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//email id open form handler
	@RequestMapping("/forgot")
	public String openEmailForm()
	{
		return "/forgot_email_form";
		
	}
    
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email,HttpSession httpSession)
	{
		System.out.println("email    "+email);
		//generate 4 digit otp 
		
		Random random= new Random(1000);
		
		int otp=random.nextInt(9999);
		
		System.out.println("OTP "+otp );
		
		String subject="OTP from SCM";
		String message="OTP is "+otp ;
				


		String to =email;
		
		boolean flag=this.emailService.sendEmail(subject, message, to);
		System.out.println("     boollean     "+flag);
	    
		if(flag)
		{
	
			httpSession.setAttribute("message", new Message("OTP sent to your mail!!", "alert-success"));
			httpSession.setAttribute("myotp", otp);
			httpSession.setAttribute("email", email);
			return "/verify_otp";
			
		}
		else
		{
			httpSession.setAttribute("message", "Check your mail ID!!");
			return "forgot_email_form";
		}
		
		
		
		
	}
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") int otp,HttpSession httpSession) {
		int myotp=(int)  httpSession.getAttribute("myotp");
		String email= (String)httpSession.getAttribute("email");
		if(otp==myotp)
		{
			
			User  user=this.userRepository.getUserByUserName(email);
			
			if(user==null)
			{
			 httpSession.setAttribute("message", "User does not exits  whith this mail ID!!")	;
			 return "forgot_email_form";
			}
			else
			{
				return "password_change_form";	
			}
			
			
		}
		else
		{
			httpSession.setAttribute("message", "Wrong otp");
			return "verify_otp";
		}
		
		
		
	}
	
	
	
	//change password
	
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newpassword") String newpassword , HttpSession session) {
		
		String emailUaer=(String) session.getAttribute("email");
		User user=this.userRepository.getUserByUserName(emailUaer);
		user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
		
		this.userRepository.save(user);
		
		session.setAttribute("message", "Password Changed ,Please Login now.");
		
		return "redirect:/signin?change=password changed successfully.";
		
		
	}
	
}
