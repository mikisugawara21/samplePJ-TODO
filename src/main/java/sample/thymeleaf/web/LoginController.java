package sample.thymeleaf.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sample.logic.exception.DuplicateUsernameException;
import sample.service.LoginService;
import sample.thymeleaf.web.form.RegisterForm;

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
                            HttpServletRequest request,
                            RedirectAttributes ra) {
        if (!loginService.login(username, password)) {
            ra.addFlashAttribute("error", "ユーザー名またはパスワードが違います");
            return "redirect:/login";
        }
        HttpSession old = request.getSession(false);
        if (old != null) old.invalidate();
        HttpSession fresh = request.getSession(true);
        fresh.setAttribute("username", username);
        return "redirect:/tasks";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Validated @ModelAttribute("registerForm") RegisterForm form,
                               BindingResult br,
                               Model model) {
        if (br.hasErrors()) {
            return "register";
        }
        try {
            loginService.register(form.getUsername(), form.getPassword());
        } catch (DuplicateUsernameException e) {
            br.rejectValue("username", "duplicate", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
}