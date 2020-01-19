/**
 * 
 */
package com.home.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.home.domain.AppUser;

/**
 * @author ADAM
 *
 */
@RepositoryRestResource

public interface AppUserRepository extends JpaRepository<AppUser, Long>{

	public AppUser findByUsername(String username);
	

	
}
