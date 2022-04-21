package ro.msg.learning.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NonNull;

@Controller
public class HelloController {
	@RequestMapping("/")
	public String home() {
		return "home.html";
	}
}