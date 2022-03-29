package ro.msg.learning.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
public class Employee {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO) 	

	   @Getter
	   @Setter
	   private int eid;
	   @Getter
	   @Setter
	   private String ename;
	   @Getter
	   @Setter
	   private double salary;
	   @Getter
	   @Setter
	   private String deg;

	   public Employee(int eid, String ename, double salary, String deg) {
	      super( );
	      this.eid = eid;
	      this.ename = ename;
	      this.salary = salary;
	      this.deg = deg;
	   }

	   public Employee( ) {
	      super();
	   }
	   
	   @Override
	   public String toString() {
	      return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", deg=" + deg + "]";
	   }
}