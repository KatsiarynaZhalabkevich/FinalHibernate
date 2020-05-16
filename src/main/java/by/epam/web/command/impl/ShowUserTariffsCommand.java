package by.epam.web.command.impl;

import by.epam.web.bean.Role;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.config.ServiceConfig;
import by.epam.web.controller.JSPPageName;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import by.epam.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowUserTariffsCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String USER_TARIFFS = "userTarifList";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String KONKR_USER = "userName";
    private final static String ERROR_MESSAGE_TEXT = "Can't get user's tariffs";
    private final static String ERROR_MESSAGE_TEXT2 = "You have no permission for this action! Please, log in! ";

    private UserService userService;

    private TarifService tarifService;

    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {
        userService = serviceConfig.userService();
        tarifService = serviceConfig.tarifService();

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String goToPage = JSPPageName.SHOW_USERS_PAGE;


        if (Role.ADMIN.equals(admin.getRole())) {

            int id = Integer.parseInt(request.getParameter(ID));
            logger.info("id =" + id);
            try {
                List<UserTarif> tariffs = tarifService.showTarifsByUserId(id);
                User user = userService.getUserById(id);
                if (tariffs != null) {
                    session.setAttribute(USER_TARIFFS, tariffs);
                    session.setAttribute(KONKR_USER, user);
                    goToPage = JSPPageName.SHOW_USERS_TARIFFS_PAGE;

                } else {
                    session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);

                }

            } catch (ServiceException e) {
                logger.error(e);
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT2);
            goToPage = JSPPageName.ERROR_PAGE;
        }

        return "redirect:/" + goToPage;

    }
}
