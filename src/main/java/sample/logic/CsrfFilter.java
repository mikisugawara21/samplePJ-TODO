package sample.logic;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class CsrfFilter implements Filter {

    private static final String CSRF_TOKEN = "csrfToken";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);

        if (session.getAttribute(CSRF_TOKEN) == null) {
            session.setAttribute(CSRF_TOKEN, UUID.randomUUID().toString());
        }

        String path = req.getRequestURI();
        boolean skipCheck = path.equals("/login") || path.equals("/register") || path.equals("/logout");

        if ("POST".equalsIgnoreCase(req.getMethod()) && !skipCheck) {
            String sessionToken = (String) session.getAttribute(CSRF_TOKEN);
            String requestToken = req.getParameter(CSRF_TOKEN);
            if (sessionToken == null || !sessionToken.equals(requestToken)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRFトークンが不正です");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}