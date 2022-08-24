package com.revature.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.Credentials;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.util.JwtTokenManager;

@RestController
@CrossOrigin(origins="*", allowedHeaders="*")
@RequestMapping("/login") // http://localhost:5000/api/login
public class AuthenticationController {

	UserService userv;
	JwtTokenManager tokenManager;
	
	@Autowired
	public AuthenticationController(UserService userv, JwtTokenManager tokenManager) {
		this.userv = userv;
		this.tokenManager = tokenManager;
	}

	@PostMapping // http://localhost:5000/api/users/login
	public User login(@RequestBody Credentials creds, HttpServletResponse response) {

		User user = userv.getByCredentials(creds); // this will throw and exception if the user dosn't exist

		if (user != null) {
			
			// IF they're in the DB, grant them a JWT token - generate the token
			String token = tokenManager.issueToken(user); // xxxxxx.yyyyyyy.zzzzzz
			
			// append the token to the header of the RESPONSE
			response.addHeader("rolodex-token", token); // issuer ifno
			response.addHeader("Access-Control-Expose-Headers", "rolodex-token");
			response.setStatus(200); // successful login
			
			// return the user
			return user; // sent back as JSON

		} else {
			// 3. otherwise deny and send 401 status
			response.setStatus(401); // 401 is an UNAUTHORIZED status
			return null; // TODO: maybe return User object with ID of 0
		}
	}
	
}
