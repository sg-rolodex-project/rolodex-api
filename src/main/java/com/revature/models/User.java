package com.revature.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 

	@Length(min=2)
	private String firstName;
	private String lastName;
	
	@NotBlank
	@Length(min=2)
	@Pattern(regexp="[a-zA-Z][a-zA-Z0-9]*")
	@Column(unique=true)
	private String username;
	
	@NotBlank
	@Column(name="pwd")
	private String password;
	
	@Email
	@Column(unique=true)
	private String email;
	
	// userId 1 -- maps to address Id 4
	// userId 2 -- maps to address ID 4
	// userId 1 maps to address id 6
	@ManyToMany
	@JoinTable(name="users_address",
	joinColumns=@JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="address_id"))
	private Set<Address> addresses;
	
//	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	@JoinColumn(name="jail_foreign_key") // creates a separate column with FK
//	Jail jailHolder;
	
	
	
	/**
	 * Regarding the validation constraints on some of the fields
	 * 
	 * @NotNull: a constrained CharSequence, Collection, Map, or Array is valid as
	 *           long as it's not null, but it can be empty.
	 * @NotEmpty: a constrained CharSequence, Collection, Map, or Array is valid as
	 *            long as it's not null, and its size/length is greater than zero.
	 * @NotBlank: a constrained String is valid as long as it's not null, and the
	 *            trimmed length is greater than zero.
	 */
	
}
