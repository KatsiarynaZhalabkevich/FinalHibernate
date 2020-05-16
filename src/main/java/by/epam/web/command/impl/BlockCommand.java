package by.epam.web.command.impl;

import by.epam.web.bean.Role;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.config.ServiceConfig;
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

public class BlockCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String BLOCK_MESSAGE = "blockMessage";
    private final static String USERS_LIST = "usersList";
    private final static String ERROR_MESSAGE_TEXT="Your session is finished or you don't have permission for this action";
    private final static String BLOCK_MESSAGE_TEXT_OK="User's blocked";
    private final static String BLOCK_MESSAGE_TEXT_NOT="User's didn't block";


  private   UserService userService;

    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {

        userService = serviceConfig.userService();

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        List<User> users = new ArrayList<>();
        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (admin != null && Role.ADMIN.equals(admin.getRole())) {
            try {
                users.addAll(userService.getUsers());
            } catch (ServiceException e) {
                logger.error(e);
            }
            if (users.size() != 0) {
                for (User user : users) {
                    if (user.getBalance() < 0) {
                        try {
                            userService.changeStatusById(user.getId(), false);
                        } catch (ServiceException e) {
                            logger.error(e);
                        }
                    }
                }
                users = new ArrayList<>();
                try {
                    users.addAll(userService.getUsers());
                } catch (ServiceException e) {
                    logger.error(e);
                }
                session.setAttribute(USERS_LIST, users);
                session.setAttribute(BLOCK_MESSAGE, BLOCK_MESSAGE_TEXT_OK);

            } else {
                session.setAttribute(BLOCK_MESSAGE, BLOCK_MESSAGE_TEXT_NOT);
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.ERROR_PAGE;
        }
       return "redirect:/"+ goToPage;
    }
}
