package by.epam.web.service.validation;

public class NoteValidation {

    private final static NoteValidation instance = new NoteValidation();
    private NoteValidation(){}
    public NoteValidation getInstance(){
        return instance;
    }
    //нужно ли проверять id юзеров и тарифов, когда делается запись???

}
