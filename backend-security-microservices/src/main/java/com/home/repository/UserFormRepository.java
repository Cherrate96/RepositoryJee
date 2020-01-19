/**
 * 
 */
package com.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.home.domain.UserForm;

/**
 * @author ADAM
 *
 */
@RepositoryRestResource

public interface UserFormRepository extends JpaRepository<UserForm, Long>{
}
