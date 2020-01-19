package com.home;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.home.domain.AppRole;
import com.home.domain.Task;
import com.home.repository.TaskRepository;
import com.home.service.AccountService;

@SpringBootApplication
@EnableEurekaClient
public class BackendUsersEtlApplication implements CommandLineRunner {
	@Autowired
	private TaskRepository taskRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BackendUsersEtlApplication.class, args);
	}
	
	  @Bean
	    BCryptPasswordEncoder getBCPE(){
	        return new BCryptPasswordEncoder();
	    }

	
	@Bean
	CommandLineRunner start(AccountService accountService)
	{
		return args->{
			accountService.saveRole(new AppRole(null, "USER"));
			accountService.saveRole(new AppRole(null, "ADMIN"));
			Stream.of("user1","user2","user3","admin").forEach(u->{
				accountService.saveUser(u, "1234", "1234");
			});
			accountService.addRoleToUser("admin", "ADMIN");

		};
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		taskRepository.save(new Task(null, "Extraction"));
		taskRepository.save(new Task(null, "Transformer"));
		taskRepository.save(new Task(null, "LOAD"));

	}
}
