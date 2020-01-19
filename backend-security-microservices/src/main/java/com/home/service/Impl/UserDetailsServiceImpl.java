/**
 * 
 */
package com.home.service.Impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.home.domain.AppUser;
import com.home.repository.AppUserRepository;
import com.home.service.AccountService;

/**
 * @author ADAM
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
@Autowired
private AccountService accountService;
	/*
	 * Cette methode pour indiquer où est ce qu'on va chercher
	 * l'utilisateur est quand on va considerer comme invalid
	 * */
@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	AppUser appUser=accountService.loadUserByUsername(username);
	if(appUser==null) throw new UsernameNotFoundException("user n'exist pas !");
	Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
	appUser.getRoles().forEach(r->{
		authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
	});
	return new User(appUser.getUsername(),appUser.getPassword(),authorities);
	}

}
