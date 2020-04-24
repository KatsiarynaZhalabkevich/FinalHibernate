package by.epam.web.command.util;

import by.epam.web.bean.Tarif;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import by.epam.web.service.impl.TarifServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Pagination {
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_MESSAGE_TEXT ="Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM ="pageNum";
    private final static String IS_LAST_PAGE = "isLastPage";
    private final static int LIMIT = 3;

    public static void makePage(HttpServletRequest request) throws ServiceException {
        TarifService tarifService = new TarifServiceImpl();
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
        tarifList = tarifService.showTariffRange((int) page, LIMIT);
        if (tarifList != null) {
            session.setAttribute(TARIFFS, tarifList);
            if (tarifList.size() < LIMIT) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        }else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        }

    }
}
