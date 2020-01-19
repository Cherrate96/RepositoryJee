/**
 * 
 */
package com.home.domain.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.domain.AppUser;

import lombok.AllArgsConstructor;

/**
 * @author 	ADAM
 *Cette class est le premiéer filtre
 */
@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	private AuthenticationManager authenticationManager;

	//pour récuperer les informations sur l'utilisateur authentifier
@Override
public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
	// TODO Auto-generated method stub
	//si l'utilisateur envoie son username et son mot de passe =...
	//on va utiliser
	try {
		//pour la déserialisation du json vers java
		AppUser appUser=new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		//on retourne un objet qui contient le username et le mo de passe
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(),appUser.getPassword()));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new  RuntimeException("Erreur dans le contenu de la requête"+e);
	}	
}
//pour généerer le token 

@Override
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
	// TODO Auto-generated method stub
	// principale : pour retourner l'utilisateur authentifier
	User user=(User)authResult.getPrincipal();
	/**
	 *Lorsqu'on a l'utilisateur on a besoin d'utiliser le JWT
	Avant il faut générer les roles,parce qu'on dans le jwt
	on mettent le username avec son role
	* */
	List<String> roles=new ArrayList<>();
	authResult.getAuthorities().forEach(a->{
		roles.add(a.getAuthority());
	});
	
	/**
	 * Création de jwt,Issuer c'est le nom d'authorité qui genere le token
	 Les claims c'est tableau
	 Le jwt se compose de 3 parties :
	 1-header
	 2-payload
	 3-signature
	 **/
	String jwt=JWT.create()
	  .withIssuer(request.getRequestURI())
	  .withSubject(user.getUsername())
	  .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
	  .withExpiresAt(new Date(System.currentTimeMillis()+SecurityParams.EXPIRATION))
	  .sign(Algorithm.HMAC256(SecurityParams.SECRET));
	/**
	Utiliser Bearer Bearer comme préfix
	*Header ayant authorization comme un nom est jwt comme valeur
	**/
response.addHeader(SecurityParams.JWT_HEADER_NAME, jwt);
	
}



}
