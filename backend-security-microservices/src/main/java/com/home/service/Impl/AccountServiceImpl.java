/**
 * 
 */
package com.home.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.home.domain.AppRole;
import com.home.domain.AppUser;
import com.home.repository.AppRoleRepository;
import com.home.repository.AppUserRepository;
import com.home.service.AccountService;

import lombok.AllArgsConstructor;


/**
 * @author ADAM
 *
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public AppUser saveUser(String username, String password, String confirmPassword) {
		// TODO Auto-generated method stub
		//verifier si le user est exist 
		AppUser user=appUserRepository.findByUsername(username);
		if(user!=null) throw new RuntimeException("Utilisateur déjà exist !");
	//si le mot de passe n'est le meme mot de passe confirmer 
		if(!password.equals(confirmPassword)) throw new RuntimeException("SVP,confirmez votre password !");
		//si l'utilisateur n'exist pas ,on va creer un user
		AppUser appUser=new AppUser();
		appUser.setUsername(username);
		//l'utilisateur qui vient être enregistrer doit être activé
		appUser.setActived(true);
		//enregistrer le mot de passe d'une manière crypté
		appUser.setPassword(bCryptPasswordEncoder.encode(password));
		//Enregistrer cet utilisateur
		appUserRepository.save(appUser);
		
		//après enregistrer l'utilisateur on doit l'affecter à un role

		addRoleToUser(username, "USER");
		return appUser;
	}
//enregistrer un role
	@Override
	public AppRole saveRole(AppRole appRole) {
		// TODO Auto-generated method stub
		return appRoleRepository.save(appRole);
	}
//Chercher un utilisateur par son username
	@Override
	public AppUser loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username);
	}
//Pour ajouter un role à un utilisateur
	@Override
	public void addRoleToUser(String username, String rolename) {
		// TODO Auto-generated method stub
		//chercher l'utilisateur
AppUser appUser=appUserRepository.findByUsername(username);
//chercher le role
AppRole appRole=appRoleRepository.findByRoleName(rolename);
//ajouter ce role a cet utilisateur
appUser.getRoles().add(appRole);

	}
	@Override
	public AppUser findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.findByUsername(username);
	}
	@Override
	public AppUser saveAppUser(AppUser u) {
		// TODO Auto-generated method stub
		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		return appUserRepository.save(u);
	}

}
