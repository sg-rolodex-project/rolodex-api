package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
// JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;


/**
 * This is a Test Suite - a grouping of unit tests for the methods
 * within one class.
 * 
 * The @ExtendWith annotation is used to load a JUnit 5 extension. 
 * JUnit defines an extension API, which allows a third-party vendor 
 * like Mockito to hook into the life cycle of running test classes and 
 * add additional functionality.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock // Mockito is a framework that leverages Reflection
	UserRepository mockUserRepo; // it build a class that has all the 
								// methods of the UserRepo Impl class
							   // but without the actual implementation
								// of those methods
	
	@InjectMocks
	UserService uServ;
	
	User dummyUser; // stub
	
	// before each test, intialize dummy user
	@BeforeEach
	void setup() throws Exception{
		this.dummyUser = new User(1, "dummyfirstname", "dummylastname", "dummyusername", "password", "dummy@mail.com", null);
				
	}
	
	// after each test, garbage collect the dummy!
	@AfterEach
	void teardown() throws Exception{
		// GC the dummy
		this.dummyUser = null; // the object gets removed from the heap
	}
	
	
	@Test
	void testDivideHappyPathScenario() {
		
	
		// call the method from the service class
		int x = 10;
		int y = 2;
		
		// assert that an expected value is equal to what it actually returns 
		int expected = 5;
		
		int actual = this.uServ.divide(x, y);
		
		assertEquals(expected, actual);
		
	}
	
	// ArithmeticException (extends RuntimeException) 
	@Test
	void testDivide_Failure_ArithmeticException() {
		
		try {
			this.uServ.divide(10, 0);
		} catch (Exception e) {
			// assert that the exception is indeed an ArithmenticException
			assertEquals(ArithmeticException.class, e.getClass());
		}
	}
	
	@Test
	void testGetById_Success() {
		
		// set up the id
		int id = 1;
		
		// program our "fake" repository to return the user with the id
		// bdd 
		given(this.mockUserRepo.findById(id)).willReturn(Optional.of(this.dummyUser));
		
		User expected = this.dummyUser;
		User actual = this.uServ.getById(id);
		
		// assert that the returned user is equal to the expected user;
		assertEquals(expected, actual);
		
	}
	
	@Test
	void testGetById_Failure_IdLessThanZero() {
		// set up the id
		int id = -900;
		
		// call this.uServ.getById(id)
		// assert that it returns null
		
		assertNull(this.uServ.getById(id));
	}
	
	// IF a user enters id 50, but that doens't exist
	// in the DB....we throw a UserNotFoundException
	@Test
	void testGetById_Failure_UserNotFoundException() {
		
		int id = 2;
		
		//	
		given(this.mockUserRepo.findById(id)).willReturn(Optional.empty());
		
		try {
			this.uServ.getById(id);
		} catch (Exception e) {
			// prove that the exception thrown is the custom one
			assertEquals(UserNotFoundException.class, e.getClass());
		}
	}

	
	/**
	 * ============== CHALLENGE ==============
	 * 
	 * Write 2 unit tests for the service layer's findByUsername() method 
	 * 
	 * One should be successful. 
	 * The other should test that the UserNotFoundException is thrown.
	 */
	
	
	

}
