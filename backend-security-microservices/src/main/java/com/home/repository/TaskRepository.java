/**
 * 
 */
package com.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.home.domain.Task;

/**
 * @author ADAM
 *
 */
@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Long> {
	

}
