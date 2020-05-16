package by.epam.web.command.impl;

import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.config.ServiceConfig;
import by.epam.web.controller.JSPPageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditProfileCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";


    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String goToPage;

        if (user != null) {
            goToPage = JSPPageName.USER_EDIT_PROFILE_PAGE;
        } else goToPage = JSPPageName.INDEX_PAGE;

        return goToPage;

    }
}
