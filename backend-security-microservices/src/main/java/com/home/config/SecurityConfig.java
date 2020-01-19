 /**
 * 
 */
package com.home.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.home.domain.security.JWTAuthenticationFilter;
import com.home.domain.security.JWTAuthorizationFiler;

/**
 * @author ADAM
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//Charger un username sachant son username
		/*s'il y a un utilisateur qui demande de s'authentifier,
		on a que appeler l'interface userdetailsservice et son detail est dans son implementation
		*/
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	//authentification basé sur les sessions
	//	http.formLogin();
		//authentification basé sur jwt,ne plus générer le csrf
		
		http.csrf().disable();
		//authentification de type stateless c'est a dire on va plus utilisé les session
		//cela veut dire que l'authentification sera faite par le jwt et non avec les sessions
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//permettre le login,pour ajouter un user on n'est pas besoin de s'authentifier
		http.authorizeRequests().antMatchers("/users/**","/login/**","/register/**").permitAll();
		//la gestion des users nécessite le role admin
		http.authorizeRequests().antMatchers("/appUsers/**").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers("/appRoles/**").hasAuthority("USER");
		//http.authorizeRequests().antMatchers("/tasks/**").hasAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/tasks/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/addtasks/**").permitAll();

		http.authorizeRequests().antMatchers("/tasks/**").hasAuthority("ADMIN");

//		http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ADMIN");

		//toutes les reqûetes nécessite une authentification
		http.authorizeRequests().anyRequest().authenticated();
		//le filtre qui va generer le token dès que l'utilisateur tente de s'authentifier
		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFiler(),UsernamePasswordAuthenticationFilter.class);

	}
	

//@Override
//protected void configure(HttpSecurity http) throws Exception {
//	http.csrf().disable()
//	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	.and()
//	.authorizeRequests()
//	.antMatchers("/users/**","/login/**")
//	.permitAll().
//	antMatchers(HttpMethod.POST,"/tasks/**").hasAuthority("ADMIN")
//	.anyRequest().authenticated()
//	.and()
//	.addFilter(new JWTAuthenticationFilter(authenticationManager()))
//	.addFilterBefore(new JWTAuthorizationFiler(),UsernamePasswordAuthenticationFilter.class);
//	
//}	
	
}
