package by.epam.web.command.impl;

import by.epam.web.command.Command;
import by.epam.web.controller.JSPPageName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(true);

        String goToPage = JSPPageName.USER_REG_PAGE;
        return "redirect:/"+goToPage;


    }
}



