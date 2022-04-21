package ro.msg.learning.shop;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Laptop {
	private int lid;
	private String Brand;
	
	public void compile() {
		System.out.println("compiling");
	}
}
