package sample.thymeleaf.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                            HttpServletRequest request,
                            RedirectAttributes ra) {
        if (!loginService.login(username, password)) {
            ra.addFlashAttribute("error", "ユーザー名またはパスワードが違います");
            return "redirect:/login";
        }
        // セッション固定攻撃対策: 既存セッションを破棄して新しく作り直す
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