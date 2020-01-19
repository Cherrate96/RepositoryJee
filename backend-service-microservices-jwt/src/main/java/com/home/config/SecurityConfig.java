/**
 * 
 */
package com.home.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.home.domain.security.JWTAuthorizationFilter;

/**
 * @author ADAM
 *
 */
@Configuration

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	

	
@Override
protected void configure(HttpSecurity http) throws Exception {
	// TODO Auto-generated method stub
	//ce n'est pas nécessaire de generer a chaque fois le token
	http.csrf().disable();
	//ce n'est plus la peine d'utiliser les sessions
	//c'est au developpeur d'ultiliser de gérer le token (JWT)
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.authorizeRequests().antMatchers(HttpMethod.GET, "/categories/**").permitAll();
	http.authorizeRequests().antMatchers(HttpMethod.GET, "/products/**").permitAll();

	http.authorizeRequests().antMatchers("/categories/**").hasAuthority("ADMIN");
	http.authorizeRequests().antMatchers("/products/**").hasAuthority("USER");
	http.authorizeRequests().anyRequest().authenticated();
	http.addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);

}

@Bean
BCryptPasswordEncoder getBCPE()
{
	return new BCryptPasswordEncoder();
}

}
