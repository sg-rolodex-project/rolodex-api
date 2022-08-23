package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsernameAndPassword(String username, String password);
	
	Optional<User> findByUsername(String username);
	
	@Query("FROM User WHERE email LIKE %:pattern") // the : is a placeholder for the argument
	Optional<List<User>> findByEmailContains(String pattern);

}
