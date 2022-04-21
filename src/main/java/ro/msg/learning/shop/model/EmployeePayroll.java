package ro.msg.learning.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EmployeePayroll {

	  private @Id @GeneratedValue Long id;
	  private String name;
	  private String role;
	  
	  public EmployeePayroll (String name, String role) {
		  this.name = name;
		  this.role = role;
	  }
}
