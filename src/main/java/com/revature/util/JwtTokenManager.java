package com.revature.util;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.exceptions.AuthenticationException;
import com.revature.models.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenManager {
	
	private final Key key;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public JwtTokenManager() {
		
		// a key is a set of public keys used to verify a token and have it parsed by our server
		key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
	}
	
	// this builds the payload which is encrypted information about the user we're authenticating
	public String issueToken(User user) {
		
		return Jwts.builder() // io.jsonwebtoken
				// payload 
				.setId(String.valueOf(user.getId()))
				.setSubject(user.getUsername())
				.setIssuer("Rolodex API") // the source that generated the token
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(key).compact();
		
	}
	
	// a method to parse a token from a request
	public int parseUserIdFromToken(String token) {
		
		try {
			return Integer.parseInt(Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					// this is the way in which we can READ user data from a token
					.parseClaimsJws(token).getBody().getId());
			
		} catch (Exception e){
			logger.warn("JWT error parsing user id from token");
			throw new AuthenticationException("Unable to parse user id from JWT. Please sign in again");
		}
		
	}
}
