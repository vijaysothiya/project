package com.smart.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
	
	public void removeMessageFromSession()
	{
		try {
			
		System.out.println("Removing message from session");
		HttpSession session=	((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		session.removeAttribute("message");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
