package by.epam.web.command.impl;

import by.epam.web.bean.Role;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.controller.JSPPageName;
import by.epam.web.service.ServiceException;
import by.epam.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowUsersCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String USERS_LIST = "usersList";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get data about users. Try later!";
    private final static int LIMIT = 3;

    @Autowired
    private UserService userService;



    @Override
    public String execute(HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(); //сессию создавать не нужно, тк пользователь уже авторизован
        User user = (User) session.getAttribute(USER);
        List<User> users = new ArrayList<>();
        long page = 1;
        String goToPage = JSPPageName.ADMIN_PAGE;

        if (Role.ADMIN.equals(user.getRole())) {

            try {
                users.addAll(userService.getUsersRange(page, LIMIT));
                session.setAttribute("pageNumU", page);

            } catch (ServiceException e) {
                logger.error(e);
            }
            if (users.size() != 0) {
                session.setAttribute(USERS_LIST, users);
                goToPage = JSPPageName.SHOW_USERS_PAGE;
            } else {
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
                goToPage = JSPPageName.ERROR_PAGE;
            }

        }
        return "redirect:/" + goToPage;
    }
}
