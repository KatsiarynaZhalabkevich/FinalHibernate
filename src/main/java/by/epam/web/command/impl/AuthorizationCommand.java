package by.epam.web.command.impl;

import by.epam.web.bean.Role;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.command.util.Pagination;
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
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class AuthorizationCommand implements Command {

    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTarifs";
    private final static String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
    private final static String LOGIN_ERROR_MESSAGE_TEXT = "Wrong login and/or password!";
    private final static String ERROR_MESSAGE_TEXT = "Can't get data about tariffs!";

    private final static Logger logger = LogManager.getLogger();

    /**
     * Метод для авторизации пользователя. Включает в себя получение списка тарифов пользователя
     * и всех тарифов для отображения на странице пользователя
     *
     * @param request
     * @throws IOException
     * @throws ServletException
     */

  private  UserService userService;

   private TarifService tarifService;


    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {

        userService = serviceConfig.userService();
        tarifService = serviceConfig.tarifService();

        String login;
        String password;
        login = request.getParameter(LOGIN);
        password = request.getParameter(PASSWORD);

        logger.info(login + "    " + password);

        HttpSession session = request.getSession(true);

        User user = null;
        String goToPage;

        try {
            user = userService.authorization(login, password);
            logger.info("after author command");
        } catch (ServiceException e) {
            logger.error(e);

        }

        if (user != null) {
            session.setAttribute(USER, user);

            try {
                List<UserTarif> tariffs = tarifService.showTarifsByUserId(user.getId()); //все тарифы какие есть
                session.setAttribute(USER_TARIFFS, tariffs);
                //   Pagination.makePage(request); //все работает, но может что-то изменить?
            } catch (ServiceException e) {
                logger.error(e);
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            }
            if (Role.ADMIN.equals(user.getRole())) {
                goToPage = JSPPageName.ADMIN_PAGE;

            } else {
                goToPage = JSPPageName.USER_AUTH_PAGE;

            }

        } else {

            session.setAttribute(LOGIN_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.INDEX_PAGE;

        }

        logger.info("Ready to go");
        logger.info(goToPage);
        return goToPage;

    }

}

