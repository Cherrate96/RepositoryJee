/**
 * 
 */
package com.home.service;

import com.home.domain.AppRole;
import com.home.domain.AppUser;

/**
 * @author ADAM
 *
 */
public interface AccountService {
	//inscrire un utilisateur
	public AppUser saveUser(String username,String password,String confirmPassword);
	/*
	 * ajouter un role.Besoin juste le nom du role 
	*/
	public AppRole saveRole(AppRole appRole);
	//Charger un utilisateur sachant son username
	public  AppUser loadUserByUsername(String username);
	//ajouter un role a un utilisateur
	public void addRoleToUser(String username,String rolename);
	public AppUser findUserByUsername (String username);
	public AppUser saveAppUser(AppUser u);

}
