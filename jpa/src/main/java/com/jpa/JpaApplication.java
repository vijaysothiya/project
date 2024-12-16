package com.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.dao.UserRep;
import com.jpa.en.User;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(JpaApplication.class, args);
		UserRep userR= context.getBean(UserRep.class);
		
//	User user=new User();
//		user.setName("vijay");
//		user.setCity("indore");
//        user.setStatus("Programmer");
//        
//        User user1=new User();
//		user.setName("vijay");
//		user.setCity("indore");
//        user.setStatus("Programmer");
//        
//     //List<User> listuser= List.of(user, user1);
//     
//      HashSet<User> userlist=  new HashSet<> (Arrays.asList(user,user1));
//        
//        Iterable<User> res= userR.saveAll(userlist);
//        
//        res.forEach(userp -> {
//        	System.out.println(user);
//        });
//        //User save= userR.save(user);
//        
//        //System.out.println(save);
//        //int k=(int) userR.count();
//        //System.out.println(k);
//                
		
//		        Optional<User> optional =userR.findById(102);
//		        User userget=optional.get();
//		        
//		        userget.setName("Rahul");
//		        
//		        User updateduser=userR.save(userget);
//		        
//		        System.out.println(updateduser);
//		        
//		Iterable<User> userList= userR.findAll();
//		//User user1=userR.save(userList);
//		userList.forEach(user1-> {System.out.println(user1);});
//		
		        
		        
		        //System.out.println(userget);
		
		//userR.deleteById(102);
//		
//		List<User> userlist= userR.findByName("vijay");
//		userlist.forEach(user1->{System.out.println(user1);});
		
//		 List<User> data= userR.getAllUser();
//		 data.forEach(user1->{System.out.println(user1);});
		
//		 List<User> userlist= userR.getUserByName("vijay");
//		 
//		 userlist.forEach(user1->{System.out.println(user1);});
		 List<User> userlist= userR.getUses();
		 userlist.forEach(user1->{System.out.println(user1);});
		 
		 
	}

}
