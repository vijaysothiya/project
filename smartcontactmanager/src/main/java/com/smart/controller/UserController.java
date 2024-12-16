
package com.smart.controller;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smart.config.UserService;
import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addCommonData(ModelMap model , Principal principal) {
		String userName= principal.getName();
		System.out.println("user name "+userName);
		
		//get the data using user name
		
	User user= 	userRepository.getUserByUserName(userName);


	model.addAttribute("user",user);
	
	System.out.println(user.toString());
		
	}
    
	@RequestMapping("/index")
	public String dashboard(ModelMap model, Principal principal)
	{
		
	List<Contact> contactCount= userService.getContactCount(principal);
	model.addAttribute("userCount", contactCount.size());
	System.out.println("Contact count "+contactCount.size());
	
	return "normal/user_dashboard";	
	}
	
	//Add Contact handler
	@GetMapping("/add-contact")
	public String openAddContact(ModelMap model)
	{
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
	
	
	
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact,
			@RequestParam("profileImage") MultipartFile file, Principal principal,HttpSession session) {
		try {
			


		String name = principal.getName();
		User user=this.userRepository.getUserByUserName(name);
		contact.setUser(user);
		
		//Image process 
		
		if(file.isEmpty())
		{
			// if file has no data then give error like message
			System.out.println("File Empty ");
			contact.setImage("contact.png");
			
		}
		else {
			//else update file name in contacts
			contact.setImage(file.getOriginalFilename());
		    File saveFile=	new ClassPathResource("static/img").getFile();
		    
		     Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		    
		    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		    
		    System.out.println("File uploaded successfully ");
			
		}
		
		
		user.getContacts().add(contact);
		
		
		
		this.userRepository.save(user);
		
		
		
		
		System.out.println("data for test new contact"+contact.toString());
		System.out.println("data added successfully");
		//success
		
		session.setAttribute("message", new Message("Contact Saved !! Add More ","success"));
		
		
		}
		catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("ERROR "+ e.getMessage());
			session.setAttribute("message", new Message("Something went wrong!! ","danger"));
		}
		
		return "normal/add_contact_form";
	}
	
	
   //show contacts
	//pagination 5 per page
	
	@GetMapping("/show-contacts/{page}")
	public String showContact(@PathVariable("page") Integer page ,ModelMap model,Principal principal) {
		
		model.addAttribute("title", "View contacts");
		
		//Contact List
		
    String userName=	principal.getName();
     User user=   this.userRepository.getUserByUserName(userName);
     
        Pageable pageable=  PageRequest.of(page,5);
     
     Page<Contact> contacts= this.contactRepository.findContactsByUser(user.getId(),pageable);
     model.addAttribute("contacts", contacts);
     model.addAttribute("currentPage",page);
     model.addAttribute("totalPage",contacts.getTotalPages());
    
    
//	    
//	    User user= this.userRepository.getUserByUserName(userName);
//	    List<Contact> contacts =    user.getContacts();	
		
		
		
		
		return "normal/show_contacts";
	}
	
	@GetMapping("/contact/{cId}")
	public String showContactDetails(@PathVariable("cId") Integer cId, ModelMap model,Principal principal) {
		
		Optional<Contact> optional=this.contactRepository.findById(cId) ;
		Contact contact=optional.get();
		
		String userName =principal.getName();
		 User user= this.userRepository.getUserByUserName(userName);
		
		if(user.getId()==contact.getUser().getId()) {
		
		model.addAttribute("model", contact); 
		}
		
		System.out.println("CID "+cId);
		return "normal/contact_details";
	}
	
	
	//deleting the contact by id 
	
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid")Integer cId, ModelMap modelMap ,Principal principal, HttpSession session) {
		
		 Contact contact   =  this.contactRepository.findById(cId).get();
		
		 User user =this.userRepository.getUserByUserName(principal.getName());
		 
		 user.getContacts().remove(contact);
		 
		 this.userRepository.save(user);
		 
		 
		 
		 
		 
//		 String userName =principal.getName();
//		 User user= this.userRepository.getUserByUserName(userName);
//		 
//		// contact.setUser(null);
//		 
//		
//		if(user.getId()==contact.getUser().getId()) {
//			
//		  contact.setUser(null);
//		 this.contactRepository.delete(contact);
		 
		 session.setAttribute("message", new Message("Contact Deleted Successfully!!", "success"));
		 System.out.println("deleting .......");
//		}
//		else {
//			System.out.println(" not delete deleting .......");
//		}
		
		return "redirect:/user/show-contacts/0";
	}
	
	//open update form handler
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid, ModelMap model)
	{
		model.addAttribute("title", "Update Contact");
		
	     Contact contact=	this.contactRepository.findById(cid).get();
	     
	     model.addAttribute("contact", contact);
		
		
		return "normal/update_form";
	}
	
	@PostMapping("/update-user/{id}")
	public String updateUserForm(@PathVariable("id") Integer id, ModelMap model)
	{
		model.addAttribute("title", "Update User");
		
	     
	     User user= this.userRepository.findById(id).get();
	     
	     model.addAttribute("user", user);
		
		
		return "normal/update_user_form";
	}
	
	
	//update contact here 
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, 
			ModelMap model, HttpSession session,Principal principal) {
		
		try {
			
			Contact oldContactDetail= this.contactRepository.findById(contact.getcId()).get();
			
			if (!file.isEmpty()) {
				
				
				//delete old profile image
				File deleteFile=	new ClassPathResource("static/img").getFile();
				File file1= new File(deleteFile,oldContactDetail.getImage());
				file1.delete();
				
				
				//update new profile image 
				
				 File saveFile=	new ClassPathResource("static/img").getFile();
				    
			     Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			    
			    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			    
			    contact.setImage(file.getOriginalFilename());
				
			}
			else {
				contact.setImage(oldContactDetail.getImage());
			}
		
			
			//User user =this.userRepository.getUserByUserName(principal.getName());
			 User user1= this.userRepository.getUserByUserName(principal.getName());
			//contact.setUser(user);
			 System.out.println(user1);
			
			contact.setUser(user1);
			
			System.out.println(contact);
			
			System.out.println(contact.getUser());
			System.out.println(contact.getName());
			
			this.contactRepository.save(contact);
			
			session.setAttribute("message", new Message("Your Contact is updated...","success"));
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		return "redirect:/user/contact/"+contact.getcId();
	}
	
	
	@GetMapping("/profile")
	public String userProfile(ModelMap model) {
		model.addAttribute("title", "Profile");
		
		return "normal/profile";
	}
	
	
	//user profile update 
	
	@PostMapping("/process-update-user")
	public String userUpdateHandler(@ModelAttribute User user, @RequestParam("userprofileImage") MultipartFile file, 
			ModelMap model, HttpSession session,Principal principal) {
		
		try {
			
			User oldUserDetail= this.userRepository.findById(user.getId()).get();
			
			if (!file.isEmpty()) {
				
				
				//delete old profile image
				File deleteFile=	new ClassPathResource("static/img").getFile();
				File file1= new File(deleteFile,oldUserDetail.getImageUrl());
				file1.delete();
				
				
				//update new profile image 
				
				 File saveFile=	new ClassPathResource("static/img").getFile();
				    
			     Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			    
			    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			    
			    user.setImageUrl(file.getOriginalFilename());
				
			}
			else {
				user.setImageUrl(oldUserDetail.getImageUrl());
			}
		
			
			//User user =this.userRepository.getUserByUserName(principal.getName());
			 //User user1= this.userRepository.getUserByUserName(principal.getName());
			//contact.setUser(user);
			 //System.out.println(user1);
			
			//contact.setUser(user1);
			
			//System.out.println(contact);
			
			//System.out.println(contact.getUser());
			//System.out.println(contact.getName());
			
			this.userRepository.save(user);
			
			session.setAttribute("message", new Message("Your Profile is updated...","success"));
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		
		return "redirect:/user/contact/"+user.getId();
	}
	
	
	//deleting the contact by id 
	
		@GetMapping("/delete-user/{id}")
		public String deleteUser(@PathVariable("id")Integer id, ModelMap modelMap ,Principal principal, HttpSession session) {
			
			 //Contact contact   =  this.contactRepository.findById(cId).get();
			 
			 User user =this.userRepository.findById(id).get();
			
			 //User user =this.userRepository.getUserByUserName(principal.getName());
			 
			 //user.getContacts().remove(contact);
			 
			 this.userRepository.delete(user);
			 
			 
			 
			 
			 
//			 String userName =principal.getName();
//			 User user= this.userRepository.getUserByUserName(userName);
//			 
//			// contact.setUser(null);
//			 
//			
//			if(user.getId()==contact.getUser().getId()) {
//				
//			  contact.setUser(null);
//			 this.contactRepository.delete(contact);
			 
			 session.setAttribute("message", new Message("Contact Deleted Successfully!!", "success"));
			 System.out.println("deleting .......");
//			}
//			else {
//				System.out.println(" not delete deleting .......");
//			}
			
			return "redirect:/logout";
		}	
		
	@GetMapping("/settings")
	
   public String openSetting(@ModelAttribute User user, ModelMap model) {
		
	model.addAttribute("title", "Setting");
	model.addAttribute("users", user);
	   
	   return "normal/settings";
   }	
	
  @PostMapping("/change-password")
  public String changePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,Principal principal, HttpSession httpSession)
  {
	  System.out.println("old passs"+oldPassword);
	  System.out.println("new passs"+newPassword);
	  
	  String userName=principal.getName();
	  User currentUser=this.userRepository.getUserByUserName(userName);
	  
	  if(this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword()))
	  {
		  currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		  this.userRepository.save(currentUser);
		  httpSession.setAttribute("message", new Message("Password changed", "succuss"));
	  }
	  else
	  {
		  httpSession.setAttribute("message", new Message("Old Password Not Match", "danger"));
		  return "redirect:/user/settings";
	  }
	  
	  return "normal/user_dashboard";
  }
}
