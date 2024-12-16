package com.smart.config;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;


@Service
public class UserService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	
	
	public List<Contact> getContactCount(Principal principal) {
		 String userName=	principal.getName();
		 
		 User user=   this.userRepository.getUserByUserName(userName);
		return contactRepository.countContactById(user.getId());
	}

}
