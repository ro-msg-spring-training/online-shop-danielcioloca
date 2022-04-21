package ro.msg.learning.shop.model;

public class OrderNotFoundException extends RuntimeException {
	
	  public OrderNotFoundException(Long id) {
	    super("Could not find employee " + id);
	  }
}