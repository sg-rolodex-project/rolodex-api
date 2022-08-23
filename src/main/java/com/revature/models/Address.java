package com.revature.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="addresses")
@Data @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(exclude = {"owners"})
@ToString(exclude = {"owners"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {
	
	@Id
	@Column(name="address_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	
	private String street; // 42 Main St.
	private String secondary; // Apt. B
	
	@Length(min=2, max=2)
	private String state; // NJ
	
	private String city; 
	private String zip; // 83838-9999 
	
	@ManyToMany(mappedBy="addresses") // this is the property in the User class
	private Set<User> owners;

	// constructor with no id and no owners field
	public Address(String street, String secondary, @Length(min = 2, max = 2) String state, String city, String zip) {
		super();
		this.street = street;
		this.secondary = secondary;
		this.state = state;
		this.city = city;
		this.zip = zip;
	}
}














