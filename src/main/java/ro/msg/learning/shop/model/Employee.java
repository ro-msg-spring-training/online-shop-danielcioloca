package ro.msg.learning.shop.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Employee {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY) 	


	   private int id;

	   private String name;
	   private String email;
	   private LocalDate date_of_birth;
}