/**
 * 
 */
package com.home.domain.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.validator.internal.util.privilegedactions.NewSchema;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.home.domain.security.SecurityParams;

/**
 * @author ADAM
 *
 */
public class JWTAuthorizationFiler  extends OncePerRequestFilter{
 
	/**
	 * Pour chaque requête envoyé par un utilisateur
	  cette methode va s'executer d'abord
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//autoriser toutes les pages du domaines à envoyer des requêtes 
		response.addHeader("Access-Control-Allow-Origin", "*");
		//autoriser seulement les entête origin ...etc
		response.addHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,authorization");
		//exposer les entêtes pour un client comme angular et on a le droit le lire la valeur du l'entête authorization
		response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials,authorization");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
		/**les requêtes envoyées par une méthode option 
			on va répondre avec OK
		**/
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			/**
			 * Quand on veut s'authentifier 
			 	on va pas lire le header authorization ,ce n'est pas obligatoire
			 */
		}
		else if(request.getRequestURI().equals("/login")) {
			filterChain.doFilter(request, response);
		}
		else {
		
		//Récuperer d'abord le header
		String jwtToken=request.getHeader(SecurityParams.JWT_HEADER_NAME);
		System.out.println("Token="+jwtToken);
		/**
		 * dés quand récupere le jwt et que le jwt ne commence
		 pas par le préfix  on va authentifier l'utilisateur
		et on va passer vers le filtre suivant
		 */
		if(jwtToken==null || !jwtToken.startsWith(SecurityParams.HEADER_PREFIX))
		{
			filterChain.doFilter(request, response);
			return;
		}
		JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
		
		//sinon on va decoder le jwt
		/**
		 * On va decoder le jwt en utilisant
		 pour faire ça on doit supprimer le prefix
		 à l'aide du methode substring
		 et prendre juste la partie après le préfix
		 */
		//signer avant decoder,la on connait que le secret
String jwt=jwtToken.substring(SecurityParams.HEADER_PREFIX.length());
		DecodedJWT decodedJWT=verifier.verify(jwt);
		System.out.println("JWT :"+jwt);
		String username=decodedJWT.getSubject();
		List<String> roles=decodedJWT.getClaims().get("roles").asList(String.class);
	 System.out.println("username:"+username+"\n roles :"+roles);
		//transformer ces roles dans une collection granthedauthroity
		Collection<GrantedAuthority> authorities=new ArrayList<>();
		for (String r : roles) {
			authorities.add(new SimpleGrantedAuthority(r));
		}
		// pour authentifier un utilisateur
		UsernamePasswordAuthenticationToken user=new UsernamePasswordAuthenticationToken(username, null,authorities);
	SecurityContextHolder.getContext().setAuthentication(user);
	
	/** 
	 * Après que l'utilisateur s'authentifier,Passer au filtre suivant
	 si l'utilisateur à le droit d'accéder il peut accéder au ressource demandée
	 NB:On peut l'accéder que à l'aide d'un client comme angular,arc...etc
	 */
	
	filterChain.doFilter(request, response);
		
	}
	}
}
