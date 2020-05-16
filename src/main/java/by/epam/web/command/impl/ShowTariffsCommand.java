package by.epam.web.command.impl;

import by.epam.web.bean.Role;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.config.ServiceConfig;
import by.epam.web.controller.JSPPageName;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowTariffsCommand implements Command {

    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT = "Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM = "pageNum";
    private final static String IS_LAST_PAGE = "isLastPage";
    private final static int LIMIT = 3;

    private TarifService tarifService;


    @Override
    public String execute(HttpServletRequest request, ServiceConfig serviceConfig) throws IOException {
        tarifService = serviceConfig.tarifService();

        logger.info("ShowTariffsCommand Level 1");
        List<Tarif> tarifList;

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);

        String goToPage = JSPPageName.ERROR_PAGE;



        try {
//надо переделать на запрос сразу в БД

            //tarifList = makePageTariff(request);
            tarifList = tarifService.showAllTarif();
            request.setAttribute(TARIFFS, tarifList);
            for(Tarif tarif: tarifList){
                System.out.println(tarif);
            }
            if (admin == null || !Role.ADMIN.equals(admin.getRole())) {

                goToPage = JSPPageName.TARIF_PAGE;
            } else {
                goToPage = JSPPageName.TARIF_ADMIN_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
        }

        return goToPage;
    }
//изменить метод!!!!
    private List<Tarif> makePageTariff(HttpServletRequest request) throws ServiceException {

        List<Tarif> tarifList;
        HttpSession session = request.getSession();

        long page;
        if (request.getParameter(PAGE_NUM) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUM));
            session.setAttribute(PAGE_NUM, page);
        } else {
            page = 1;
            session.setAttribute(PAGE_NUM, page);
        }

        tarifList = tarifService.showTariffRange((int) page-1, LIMIT);
        if (tarifList != null) {
            session.setAttribute(TARIFFS, tarifList);
            if (tarifList.size() < LIMIT) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }
        return tarifList;

    }
}
