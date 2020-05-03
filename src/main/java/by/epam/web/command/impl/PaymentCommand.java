package by.epam.web.command.impl;


import by.epam.web.bean.Role;
import by.epam.web.bean.User;
import by.epam.web.command.Command;
import by.epam.web.controller.JSPPageName;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import by.epam.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentCommand implements Command {
    private final static Logger logger = LogManager.getLogger();
    private final static String USER = "user";
    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String PAYMENT_MESSAGE = "paymentMessage";
    private final static String USERS_LIST = "usersList";
    private final static String PAYMENT_MESSAGE_TEXT="Payment OK";
    private final static String ERROR_MESSAGE_TEXT_PAY="Can't get users for payment!";
    private final static String ERROR_MESSAGE_TEXT= "Session is not valid!";

    @Autowired
  private   UserService userService;
    @Autowired
  private   TarifService tarifService;
    @Override
    public String execute(HttpServletRequest request) throws IOException {



        List<UserTarif> userTariffs = new ArrayList<>();
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(USER);
        List<User> userList = new ArrayList<>();
        String goToPage = JSPPageName.SHOW_USERS_PAGE;

        if (admin != null && admin.getRole().equals(Role.ADMIN)) {
            //получаем всех юзеров
            try {
                userList.addAll(userService.getUsers());
            } catch (ServiceException e) {
                logger.error(e);
            }

            if (userList.size() != 0) {
                for (User user : userList) {
                    try {
                        userTariffs = tarifService.showTarifsByUserId(user.getId());
                    } catch (ServiceException e) {
                        logger.error(e);
                    }
                    if (userTariffs != null) {
                        double payment = paymentToWithdraw(userTariffs);
                        try {
                            if (!userService.changeBalanceById(user.getId(), payment)) {
                                logger.error(user.getId() + PAYMENT_MESSAGE_TEXT);
                            }

                        } catch (ServiceException e) {
                            logger.error(e);
                        }
                    }
                }
                session.setAttribute(PAYMENT_MESSAGE, PAYMENT_MESSAGE_TEXT);
                //надо получить всех юзеров заново с новой информацией
                //использовать пагинацию!!! а то вывалятся новые юзеры
                try {
                    userList = new ArrayList<>();
                    userList.addAll(userService.getUsers());
                    session.setAttribute(USERS_LIST, userList);
                } catch (ServiceException e) {
                    logger.error(e);
                }

            } else {
                session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT_PAY);
                goToPage = JSPPageName.ERROR_PAGE;
            }

        } else {
            session.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
            goToPage = JSPPageName.ERROR_PAGE;
        }
        return "redirect:/"+goToPage;
    }

    private double paymentToWithdraw(List<UserTarif> userTariffs) {
        double payment = 0;
        for (UserTarif t : userTariffs) {
            payment -= t.getTarif().getPrice()- t.getTarif().getPrice() * t.getTarif().getDiscount() * 0.01;

        }
        return payment;
    }
}
