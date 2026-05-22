package sample.thymeleaf.web;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sample.service.LoginService;

@Controller
public class LoginController {
	
	private final LoginService loginService;

	public LoginController(LoginService loginService) {
	    this.loginService = loginService;
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginPost(@RequestParam String username, 
			                @RequestParam String password,
			                HttpSession session) {
		boolean result = loginService.login(username, password);
		if (result) {
			session.setAttribute("username", username);
			return "redirect:/tasks";
		}
		return "redirect:/login";
	}
	
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@RequestParam String username,
			                   @RequestParam String password) {
		loginService.register(username, password);
		return "redirect:/login";
	}
	
}
