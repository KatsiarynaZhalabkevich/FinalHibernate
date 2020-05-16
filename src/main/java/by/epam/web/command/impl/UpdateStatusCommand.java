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
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UpdateStatusCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String ACTIVE = "active";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_STATUS = "updStatusMessage";
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String USERS_LIST = "usersList";
    private final static String UPD_STATUS_OK = "Status updated!";
    private final static String UPD_STATUS_NOT = "Can't upd user status";
    private final static String ERROR_MESSAGE_TEXT = "You have no permission for this action! Please, log in! ";


    private UserService userService;

    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {

        userService = serviceConfig.userService();
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER); //admin
        int id = Integer.parseInt(request.getParameter(ID));
        String status = request.getParameter(ACTIVE);
        boolean active = false;

        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (admin.getRole().equals(Role.ADMIN)) {
            try {
                if (ACTIVE.equals(status)) {
                    active = true;
                }
                if (userService.changeStatusById(id, active)) {
                    session.setAttribute(UPD_STATUS, UPD_STATUS_OK);
                    List<User> users = userService.getUsers();//запрашивать всех сразу
                    session.setAttribute(USERS_LIST, users);
                } else {
                    session.setAttribute(UPD_STATUS, UPD_STATUS_NOT);
                }

            } catch (ServiceException e) {
                logger.error(e);
            }
        } else {

            goToPage = JSPPageName.ERROR_PAGE;
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        return "redirect:/" + goToPage;
    }
}
