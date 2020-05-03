package by.epam.web.command.impl;

import by.epam.web.command.Command;
import by.epam.web.controller.JSPPageName;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class NoSuchCommand implements Command {
    private final static String ERROR_MESSAGE="errorMessage";
    private final static String ERROR_MESSAGE_TXT="Sorry, this action can't be done!";

    @Override
    public String execute(HttpServletRequest request) throws IOException {
        String goToPage= JSPPageName.ERROR_PAGE;
        request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TXT);

        return "redirect:/"+goToPage;
    }
}
