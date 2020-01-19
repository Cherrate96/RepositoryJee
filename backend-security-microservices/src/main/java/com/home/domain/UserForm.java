/**
 * 
 */
package com.home.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author ADAM
 *
 */
@Data
@Entity
public class UserForm {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String username;
	private String password;
	private String confirmedPassword;
	public UserForm(String username, String password, String confirmedPassword) {
		super();
		this.username = username;
		this.password = password;
		this.confirmedPassword = confirmedPassword;
	}
	public UserForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
