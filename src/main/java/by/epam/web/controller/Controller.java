package by.epam.web.controller;

import by.epam.web.command.Command;
import by.epam.web.command.CommandProvider;
import by.epam.web.config.ServiceConfig;
import by.epam.web.service.NoteService;
import by.epam.web.service.TarifService;
import by.epam.web.service.UserService;
import by.epam.web.tag.TimeJSPTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@org.springframework.stereotype.Controller
//@RequestMapping("/controller")
public class Controller {
    private final static long serialVersionUID = 1L;
    private final static Logger logger = LogManager.getLogger();
    @Autowired
    private CommandProvider provider;

   private ServiceConfig serviceConfig;


    private final static String COMMAND = "command";

    public Controller(ServiceConfig serviceConfig) {
        super();
        this.serviceConfig = serviceConfig;

    }

    @GetMapping("/controller")
    public String doGet(HttpServletRequest request) throws IOException {

        return doPost(request);
    }

    @PostMapping("/controller")
    public String doPost(HttpServletRequest request) throws IOException {

        String commandName;
        Command command;

        if (request.getParameter("local") != null) {
            request.getSession(true).setAttribute("local", request.getParameter("local"));
            if (request.getParameter("local").equals("ru")) {
                TimeJSPTag.locale = new Locale("ru");
            } else {
                TimeJSPTag.locale = Locale.ENGLISH;
            }
            logger.info(request.getParameter("local"));
            return "redirect:/"+JSPPageName.INDEX_PAGE;

        }

        commandName = request.getParameter(COMMAND);
        logger.info("Provider "+provider);

        command = provider.getCommand(commandName);
        logger.info("Command name " + commandName);
        return command.execute(request, serviceConfig);


    }


}
