package by.epam.web.command.impl;

import by.epam.web.bean.Note;
import by.epam.web.bean.Role;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.controller.JSPPageName;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ID = "user_id";
    private final static String USER_TARIFFS = "userTarifList";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String KONKR_USER = "userName";
    private final static String ERROR_MESSAGE_TEXT = "Can't get user's tariffs";
    private final static String ERROR_MESSAGE_TEXT2 = "You have no permission for this action! Please, log in! ";
    private final static String TARIFFS = "tarifs";
    private final static String USERS_LIST = "usersList";
    @Autowired
 private    UserService userService;
    @Autowired
 private    TarifService tarifService;
    @Autowired
 private    NoteService noteService;

    @Override
    public String execute(HttpServletRequest request) throws IOException {


        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        String goToPage = JSPPageName.ADMIN_PAGE;
        Map<Integer, Integer> tariffsCount = new HashMap<>();


        if (Role.ADMIN.equals(admin.getRole())) {

            try {
                List<User> users = userService.getUsers();

                List<Tarif> tariffs = tarifService.showAllTarif();

                int count = 0;
                for (Tarif t : tariffs) {
                    List<Note> notes = noteService.findNoteByTarifId(t.getId());
                    count += notes.size();
                    tariffsCount.put(t.getId(), notes.size());

                }

                session.setAttribute("user_number", users.size());
                session.setAttribute("tariff_number", tariffs.size());
                session.setAttribute("tariff_count", tariffsCount);
                session.setAttribute("connectionsCount", count);
                session.setAttribute(TARIFFS, tariffs);

                goToPage = JSPPageName.STATISTIC_PAGE;

            } catch (ServiceException e) {
                logger.error(e);
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT2);
            goToPage = JSPPageName.ERROR_PAGE;
        }

        return goToPage;
    }
}
