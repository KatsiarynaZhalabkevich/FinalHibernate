package by.epam.web.service;

import by.epam.web.service.impl.NoteServiceImpl;
import by.epam.web.service.impl.TarifServiceImpl;
import by.epam.web.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {
    }

    private UserService userService = new UserServiceImpl();
    private TarifService tarifService = new TarifServiceImpl();
    private NoteService noteService = new NoteServiceImpl();


    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }
    public TarifService getTarifService(){return tarifService;}
    public NoteService getNoteService(){return noteService;}

}
