package com.login.controller;

import javax.validation.Valid;
import javax.validation.Validation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.Validate;

import com.login.entity.Logindata;



@Controller
public class MyController {
	
	@GetMapping("/form")
	public String openform(ModelMap model) {
		
		System.out.println("Loging");
		model.addAttribute("logindata", new Logindata());
		
		return "form";
	}
	
	@PostMapping("/process")
	public String processForm(@Valid @ModelAttribute("logindata") Logindata logindata, BindingResult result) {
		
		
		if(result.hasErrors())
		{
			System.out.println(result);
			return "form";
		}
		System.out.println(logindata);
		return "success";
	}

}
