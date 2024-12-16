package com.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jpa.en.User;

public interface UserRep extends CrudRepository<User, Integer> {
	
	public List<User> findByName(String name);
	
	@Query("select u from User u")
	public List<User> getAllUser();
	
	@Query("select u from User u where u.name= :n")
	public List<User> getUserByName(@Param("n") String name);
	
	@Query(value="select * from User", nativeQuery = true)
	public List<User> getUses();
	
	

}
