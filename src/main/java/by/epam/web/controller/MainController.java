package by.epam.web.controller;


import by.epam.web.bean.Role;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.command.util.Pagination;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import by.epam.web.service.UserService;
import by.epam.web.tag.JSPListBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/telecom")
public class MainController {
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String USER = "user";
    private final static String TARIFFS = "tarifs";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String USER_TARIFFS = "userTarifs";
    private final static String LOGIN_ERROR_MESSAGE = "loginErrorMessage";
    private final static String LOGIN_ERROR_MESSAGE_TEXT = "Wrong login and/or password!";
    private final static String ERROR_MESSAGE_TEXT = "Can't get data about tariffs!";

    private final static String ERROR_MESSAGE_TEXT_TARIFF = "Can't get information about tariffs. Please, try later";
    private final static String PAGE_NUM = "pageNum";
    private final static String IS_LAST_PAGE = "isLastPage";
    private final static int LIMIT = 3;

    private final static Logger logger = LogManager.getLogger();

    private UserService userService;
    private TarifService tarifService;

    public MainController(UserService userService, TarifService tarifService) {
        this.userService = userService;
        this.tarifService = tarifService;
    }

    @GetMapping(value = {"/login", "/"})
    public String login(HttpServletRequest request) {

//    String login;
//    String password;
//    login = request.getParameter(LOGIN);
//    password = request.getParameter(PASSWORD);

//    HttpSession session = request.getSession(true);
//
//    User user = null;
//    String goToPage;
//
//    try {
//        user = userService.authorization(login, password);
//        System.out.println(user);
//    } catch (ServiceException e) {
//        logger.error(e);
//
//    }
//
//    if (user != null) {
//        session.setAttribute(USER, user);
//
//        try {
//            List<UserTarif> tariffs = tarifService.showTarifsByUserId(user.getId()); //все тарифы какие есть
//            session.setAttribute(USER_TARIFFS, tariffs);
//            Pagination.makePage(request); //все работает, но может что-то изменить?
//        } catch (ServiceException e) {
//            logger.error(e);
//            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
//        }
//        if (Role.ADMIN.equals(user.getRole())) {
//            goToPage = JSPPageName.ADMIN_PAGE;
//
//        } else {
//            goToPage = JSPPageName.USER_AUTH_PAGE;
//
//        }
//
//    } else {
//
//        session.setAttribute(LOGIN_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE_TEXT);
//        goToPage = JSPPageName.INDEX_PAGE;
//
//    }

        System.out.println("inside login command");
        logger.info("inside login command");
        return "admin";


    }

    @GetMapping("/page/show_tariffs")
    public String showTariffs(HttpServletRequest request) {

        logger.info("ShowTariffsCommand Level 1");
        List<Tarif> tarifList;

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);

        String goToPage = JSPPageName.ERROR_PAGE;
        logger.info(request.getPathInfo());
        logger.info(request.getServletPath());
        logger.info(request.getRequestURL());


        try {
//надо переделать на запрос сразу в БД
            logger.info("Ready to go to BD");
            tarifList = makePageTariff(request);
            if (admin == null || !Role.ADMIN.equals(admin.getRole())) {
                JSPListBean jspListBean = new JSPListBean(tarifList);
                session.setAttribute("userbean", jspListBean);
                logger.info("After BD");
                goToPage = JSPPageName.TARIF_PAGE;
            } else {
                goToPage = JSPPageName.TARIF_ADMIN_PAGE;
            }
        } catch (ServiceException e) {
            logger.error(e);
        }

        return "redirect:/" + goToPage;
    }

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
        tarifList = tarifService.showTariffRange((int) page, LIMIT);
        if (tarifList != null) {
            session.setAttribute(TARIFFS, tarifList);
            if (tarifList.size() < LIMIT) {
                session.setAttribute(IS_LAST_PAGE, true);
            } else {
                session.setAttribute(IS_LAST_PAGE, false);
            }
        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT_TARIFF);
        }
        return tarifList;

    }
}
