package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;


import org.springframework.ui.*;
import org.springframework.validation.BindingResult;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	

	@RequestMapping("/")
	public String home(ModelMap model)
	{
		model.addAttribute("title", "HOme- smart contact manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model)
	{
		model.addAttribute("title", "About- smart contact manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(ModelMap model)
	{
		model.addAttribute("title", "Register- smart contact manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	// REGISTER HANDLER
	@RequestMapping(value="/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,
			@RequestParam(value="agreement", defaultValue = "false")boolean agreement,@RequestParam(name = "userprofileimage") MultipartFile file, ModelMap model,  HttpSession session)
	
	{ 
		
		
		try {
			
			
			if(!agreement)
			{
				System.out.println("You have not clicked on check box.");
				throw new Exception("You have not clicked on check box.");
				
			}
			if(bindingResult.hasErrors())
			{
				System.out.println("ERROR"+bindingResult.toString());
				model.addAttribute("user", user);
				
				return "signup";
			}
			
			if(file.isEmpty())
			{
				// if file has no data then give error like message
				System.out.println("File Empty ");
				user.setImageUrl("contact.png");
				
				
			}
			else {
				//else update file name in contacts
				user.setImageUrl(file.getOriginalFilename());
			    File saveFile=	new ClassPathResource("static/img").getFile();
			    
			     Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			    
			    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			    
			    System.out.println("File uploaded successfully ");
				
			}

			
			
			
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("agreement"+agreement);
			System.out.println("USER"+user);
			
			User result= this.userRepository.save(user);
			model.addAttribute("user", new User());
			
			System.out.println("yha hu me ");
			session.setAttribute("message", new Message("Successfully Reg", "alert-success"));
			System.out.println("in exception vijay belo1 ");
			return "signup";
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("in exception vijay");
			e.printStackTrace();
			model.addAttribute("user", user);
			
			Message message=new Message("Something went wrong"+ e.getMessage(), "alert-danger");
			
			session.setAttribute("message", message);
			
			
			System.out.println("in exception vijay belo2 ");
		
			
			return "signup";
		}
		
		
		
		
	}
	
	//handleer for custom login
	@GetMapping("/signin")
	public String customLogin(ModelMap model) {
		model.addAttribute("title","Login Page");
		return "login";
	}
	
//	@Autowired
//	private UserRepository userRepository;
//	
//	
//	@GetMapping("/test")
//	@ResponseBody
//	public String test() {
//		
//		User user= new User();
//		
//		user.setName("vijay");
//		user.setEmail("vijay@gmail.com");
//		
//		userRepository.save(user);
//		
//		return "working";
//	}

	
	
}
