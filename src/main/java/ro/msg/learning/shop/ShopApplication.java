package ro.msg.learning.shop;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
		String[] arr = new String[] { "a", "b", "c" };
		Stream<String> streamOfArrayFull = Arrays.stream(arr);
		streamOfArrayFull.forEach(i -> System.out.println(i));
/*
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TestPersistence");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Employee employee = new Employee();
		employee.setEid(100);
		employee.setEname("daniel");
		employee.setDeg("alex");
		employee.setSalary(10.5);

		 entitymanager.merge( employee );
		 entitymanager.getTransaction( ).commit( );
		 
		 entitymanager.close( );
		 emfactory.close( );*/
	}

	@Configuration
	@EnableWebSecurity
	public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.authorizeRequests().antMatchers("/h2/*").permitAll();
			http.csrf().disable();
			http.headers().frameOptions().disable();
		}
	}
}
