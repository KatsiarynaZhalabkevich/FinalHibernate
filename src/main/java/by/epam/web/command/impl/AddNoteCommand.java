package by.epam.web.command.impl;

import by.epam.web.bean.Note;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.config.ServiceConfig;
import by.epam.web.controller.JSPPageName;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.NoteService;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AddNoteCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFF_ID = "tarif_id";
    private final static String UPD_MESSAGE = "updateMessage";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTarifs";

    /**
     * Метод для добавления новой записи о тарифе пользователя
     *
     * @param request
     * @throws IOException
     * @throws ServletException
     */

    private NoteService noteService;
    private TarifService tarifService;


    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {
        noteService = serviceConfig.noteService();
        tarifService = serviceConfig.tarifService();

        HttpSession session = request.getSession(); //только зарегистрированный пользователь может добавить себе тариф
        User user = (User) session.getAttribute(USER);
        int tarifId = Integer.parseInt(request.getParameter(TARIFF_ID)); //удобнее сразу тариф забирать

        String goToPage = JSPPageName.USER_AUTH_PAGE;


        if (user != null) {
            Note note = new Note();
            note.setUser(user);
            Tarif tarif = null;
            try {
                tarif = tarifService.showTarifById(tarifId);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            note.setTariff(tarif);

            try {
                if (noteService.addNote(note)) {//добавили в БД
                    request.setAttribute(UPD_MESSAGE, "New tariff is added!");
                    List<UserTarif> tariffs = tarifService.showTarifsByUserId(user.getId());
                    session.setAttribute(USER_TARIFFS, tariffs);
                } else {
                    request.setAttribute(UPD_MESSAGE, "Can't add tariff to account!");
                }

            } catch (ServiceException e) {
                logger.error(e);
            }

        } else {
            request.setAttribute(ERROR_MESSAGE, "Session is expired. Please, sign in!");
            goToPage = JSPPageName.ERROR_PAGE;
            return "redirect:/" + goToPage;
        }

        return "redirect:/" + goToPage;


    }
}
