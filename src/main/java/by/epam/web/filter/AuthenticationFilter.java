package by.epam.web.filter;

import by.epam.web.bean.User;
import by.epam.web.controller.JSPPageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="No permission for this action! Please, log in!";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        logger.info("фильтр аутентификации");

        if (user == null) {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            response.sendRedirect(JSPPageName.ERROR_PAGE);

        }

               chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
