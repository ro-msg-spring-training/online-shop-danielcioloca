package ro.msg.learning.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Supplier {
	private int sid;
	private String sname;
	private String tech;
	@Autowired
	private Laptop laptop;

	public void show() {
		System.out.println("in show");
		laptop.compile();
	}

	public Supplier() {
		super();
		System.out.println("object supplier created");
	}
}
