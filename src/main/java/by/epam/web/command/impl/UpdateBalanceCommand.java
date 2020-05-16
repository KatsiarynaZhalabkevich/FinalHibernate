package by.epam.web.command.impl;

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


public class UpdateBalanceCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String BALANCE = "balance";
    private final static String UPD_BALANCE_MESS = "updBalanceMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String UPD_BALANCE_MESS_OK = "Balance updated!";
    private final static String UPD_BALANCE_MESS_NOT = "Can't upd user balance";
    private final static String ERROR_MESSAGE_TEXT = "Can't upd user balance. Please, sign in and try again";


    private UserService userService;

    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {

        userService = serviceConfig.userService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER); //по ключу получаем значение
        String goToPage = JSPPageName.USER_AUTH_PAGE;
        double balance = Double.parseDouble(request.getParameter(BALANCE));

        if (user != null) {
            user.setBalance(balance);
            try {
                if (userService.saveUpdateUser(user)) {
                    session.setAttribute(UPD_BALANCE_MESS, UPD_BALANCE_MESS_OK);
                    session.setAttribute(USER, user);
                } else {
                    session.setAttribute(UPD_BALANCE_MESS, UPD_BALANCE_MESS_NOT);
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
