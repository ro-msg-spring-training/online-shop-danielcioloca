package ro.msg.learning.shop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMER_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  private @Id @GeneratedValue Long id;

  private String description;
  private Status status;
  
  public Order(String description, Status status) {
	super();
	this.description = description;
	this.status = status;
}
  
  

}