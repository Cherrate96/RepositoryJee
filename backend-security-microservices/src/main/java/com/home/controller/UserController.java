/**
 * 
 */
package com.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.home.domain.AppRole;
import com.home.domain.AppUser;
import com.home.domain.UserForm;
import com.home.repository.AppRoleRepository;
import com.home.repository.AppUserRepository;
import com.home.service.AccountService;

/**
 * @author ADAM
 *
 */
@RestController
public class UserController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private AppRoleRepository appRoleRepository;
	
	@PostMapping("/register")
	//@requestbody : les requêtes sont envoyées en format json 
	public AppUser register(@RequestBody UserForm userForm)
	{
	return 	accountService.saveUser(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
	}
	
	@RequestMapping(value = "/listUsers",method = RequestMethod.GET)
	public List<AppUser> findUsers()
	{
		List<AppUser> appUsers=appUserRepository.findAll();
		for (AppUser au : appUsers) {
			au.getUsername();
		}
return appUsers;
	}
	
	@RequestMapping(value = "/listRoles",method = RequestMethod.GET)
	public List<AppRole> findRoles()
	{
		List<AppRole> appRoles=appRoleRepository.findAll();
		for (AppRole appRole : appRoles) {
			appRole.getRoleName();
		}
		return appRoles;
	}
	
	
	@PostMapping("/users")
	public AppUser signUp(@RequestBody UserForm data)
	{
		String username=data.getUsername();
		AppUser user=accountService.findUserByUsername(username);
		if(user!=null) throw new RuntimeException("cet utilisateur est déja exist,Veuillez insérer un autre");
		String password=data.getPassword();
		String confirmedPassword=data.getPassword();
		if(!password.equals(confirmedPassword))
			throw new RuntimeException("Vous devez confirmez votre mot de passe !");
		AppUser u=new AppUser();		
		u.setUsername(username);
		u.setPassword(password);
		accountService.saveAppUser(u);
		accountService.addRoleToUser(username, "ADMIN");
		return u;
	}
	

}
