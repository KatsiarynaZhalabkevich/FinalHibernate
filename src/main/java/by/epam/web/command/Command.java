package by.epam.web.command;

import by.epam.web.config.ServiceConfig;
import by.epam.web.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException;
}
