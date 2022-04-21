package ro.msg.learning.shop.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import ro.msg.learning.shop.model.Employee;

public interface PersonRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> findByNameAndEmail(String name, String email);

}
