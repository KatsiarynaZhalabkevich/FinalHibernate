package by.epam.web.command.impl;

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
import java.util.List;

public class UserPaginationCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USERS_LIST = "usersList";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get information about users. Please, try later";
    private static final String PAGE_NUM = "pageNumU";
    private static final String IS_LAST_PAGE = "isLastPageU";
    private static final int SIZE = 3;

    @Autowired
    private UserService userService;

    @Override
    public String execute(HttpServletRequest request) throws IOException {

        List<User> userList = null;
        long page;
        HttpSession session = request.getSession();

        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (request.getParameter(PAGE_NUM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUM));
            session.setAttribute(PAGE_NUM, page);
        } else {
            page = 1;
            session.setAttribute(PAGE_NUM, page);
        }


        try {
            userList = userService.getUsersRange(page, SIZE);
        } catch (ServiceException e) {
            logger.error(e);
        }


        if (userList != null) {
            session.setAttribute(USERS_LIST, userList);
            if (userList.size() < SIZE) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.ERROR_PAGE;
        }

        return "redirect:/"+goToPage;
    }
}
