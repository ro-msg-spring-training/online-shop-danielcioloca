package ro.msg.learning.shop;

import java.sql.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;

import lombok.Data;
import ro.msg.learning.shop.interfaces.EmployeeRepository;
import ro.msg.learning.shop.interfaces.PersonRepository;
import ro.msg.learning.shop.interfaces.Vehicle;
import ro.msg.learning.shop.model.Employee;
import ro.msg.learning.shop.model.Person;

@SpringBootApplication
@Data
@Configuration
public class ShopApplication {
    
	@Autowired
	private static PersonRepository personRepository;
	
	ShopApplication(PersonRepository personRepository){
		this.personRepository = personRepository;
	}
	
	String testVehicle = Vehicle.producer();
	
	public static int increment(int incr) 
	{ return incr ++ ;}		
	static Function<Integer, Integer> incrementFunctionByOne = increment -> increment + 1;
	static Function<Integer, Integer> multiply = result -> result * result;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ShopApplication.class, args);
		
		List<Person> people	= Arrays.asList(
				new Person("Daniel", "Cioloca", 20),
				new Person("Alexandru", "Cioloca", 25),
				new Person("Andrei", "Cioloca", 2),
				new Person("Andreea", "Cioloca", 15)
				);
		
		Collections.sort(people, (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()));
		
		for(Person p : people){
			System.out.println(p);
		}
		
		people.stream()
			.forEach(person-> {person.getFirstName().startsWith("D"); System.out.println(person);});
		
		
		Vehicle testLambda = () -> System.out.println("test");
		
		List number = Arrays.asList(5,2,8,4);
		List square = (List) number.stream().map(multiply).collect(Collectors.toList());
		
		System.out.println(square);
		
		System.out.println(incrementFunctionByOne.apply(5));
				
		Supplier sup = context.getBean(Supplier.class);
		sup.show();

		List<Employee> test = personRepository.findByNameAndEmail("daniel", "daniel@mail.com");
		
		for(Employee employee  : test) {
			System.out.println(employee);
		}
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TestPersistence");
		EntityManager entitymanager = emfactory.createEntityManager();
		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Employee> from = criteriaQuery.from(Employee.class);
		entitymanager.getTransaction().begin();

		Employee employee1 = new Employee();
		employee1.setId(100);
		employee1.setName("danieeeeeeeel");
		employee1.setEmail("danie@gmail.com");
		employee1.setDate_of_birth(LocalDate.of(1986, 07, 22));

		 entitymanager.merge( employee1 );
		 
		Employee employee2 = new Employee(101, "alexandru", "alexandru@yahoo.com", LocalDate.of(1985, 05, 10));
			
		 entitymanager.merge( employee2 );
		 
		 entitymanager.getTransaction( ).commit( );
		 
		//select all records
		   System.out.println("Select all records");
		   CriteriaQuery<Object> select = criteriaQuery.select(from);
		   TypedQuery<Object> typedQuery = entitymanager.createQuery(select);
		   List<Object> resultlist = typedQuery.getResultList();

		   for(Object o:resultlist) {
		      Employee e = (Employee)o;
		      System.out.println("id : " + e.getId() + " name : " + e.getName());
		   }

		   //Ordering the records 
		   System.out.println("Select all records by follow ordering");
		   CriteriaQuery<Object> select1 = criteriaQuery.select(from);
		   select1.orderBy(criteriaBuilder.asc(from.get("name")));
		   TypedQuery<Object> typedQuery1 = entitymanager.createQuery(select);
		   List<Object> resultlist1 = typedQuery1.getResultList();

		   for(Object o:resultlist1){
		      Employee e=(Employee)o;
		      System.out.println("id : " + e.getId() + " name : " + e.getName());
		   }
		 
		 entitymanager.close();
		 emfactory.close();
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
