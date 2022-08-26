package com.revature.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dto.Credentials;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.AddressRepository;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private UserRepository userRepo;
	private AddressRepository addressRepo; // the save() method

	@Autowired
	public UserService(UserRepository userRepo, AddressRepository addressRepo) {
		super();
		this.userRepo = userRepo;
		this.addressRepo = addressRepo;
	}
	
	public int divide(int x, int y ) throws ArithmeticException{
		
		return x/y;
	}
	

	// login method
	public User getByCredentials(Credentials creds) { // A DTO is an object that represents the BARE MINIMUM of the data we need

		Optional<User> userInDb = userRepo.findByUsernameAndPassword(creds.getUsername(), creds.getPassword());

		if (userInDb.isPresent()) {
			
			log.info("Found user with username {}", creds.getUsername());
			
			// assign a JWT token to the response
			// so now the client can re-access the server after having logged in
			
			return userInDb.get(); // .get() method returns the VALUE of the User object inside the Optional object
		} else {
			log.warn("Username & password combination did not match for user {}", creds.getUsername());
			return null;
		}
	}

	
	// return a set of all users in the DB

	@Transactional(readOnly = true)
	public Set<User> getAll() {

		return userRepo.findAll().stream().collect(Collectors.toSet());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public User add(User u) {

		// if the address set is NOT null, iterate over each address and .save() them
		if (u.getAddresses() != null) {
			u.getAddresses().forEach(a -> addressRepo.save(a));
		}

		return userRepo.save(u);
	}

	@Transactional(propagation = Propagation.REQUIRED) // default setting of transactions in Spring
	public void remove(int id) {
		userRepo.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) throws UserNotFoundException{

		return userRepo.findByUsername(username) // in the case that no User object can be returned, throw an exception
				.orElseThrow(() -> new UserNotFoundException("No user found with username " + username));
	}
	
	@Transactional(readOnly = true)
	public User getById(int id) { 

		if (id <= 0) {
			log.warn("Id cannot be <= 0. Id passed was: {}", id);
			return null;
		} else {
			return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with id " + id));
		}

	}
}
