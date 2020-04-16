package by.epam.web.dao;

import by.epam.web.bean.Tarif;
import by.epam.web.dto.UserTarif;

import java.util.List;

public interface TarifDAO {

    boolean addTarif(Tarif tarif) throws DAOException;
    boolean editTarif(Tarif tarif) throws DAOException;
    Tarif getTarifById(int id) throws DAOException;
    List<Tarif> getAll()throws DAOException;
    boolean deleteTarifById(int id) throws DAOException;
    List<UserTarif>getTarifByUserId(int id) throws DAOException;
    List<Tarif> getTariffRange(int firstPosition, int limit) throws DAOException;

}
