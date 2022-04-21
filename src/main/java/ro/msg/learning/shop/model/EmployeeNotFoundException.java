package ro.msg.learning.shop.model;

import lombok.Data;

public class EmployeeNotFoundException extends RuntimeException {
	
	  public EmployeeNotFoundException(Long id) {
	    super("Could not find employee " + id);
	  }
}