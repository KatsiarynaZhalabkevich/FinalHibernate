package by.epam.web.command.impl;

import by.epam.web.command.Command;
import by.epam.web.config.ServiceConfig;
import by.epam.web.controller.JSPPageName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutCommand implements Command {
    private final static String USER="user";
    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(USER);
        session.invalidate();
       return JSPPageName.INDEX_PAGE;
    }
}
