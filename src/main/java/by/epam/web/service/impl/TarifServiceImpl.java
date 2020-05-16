package by.epam.web.service.impl;

import by.epam.web.bean.Tarif;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dto.UserTarif;
import by.epam.web.service.ServiceException;
import by.epam.web.service.TarifService;
import by.epam.web.service.validation.TarifDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TarifServiceImpl implements TarifService {

    private final static Logger logger = LogManager.getLogger();
    @Autowired
    private TarifDAO tarifDAO;

    private final static TarifDataValidator validator = TarifDataValidator.getInstance();

    @Override
    public List<Tarif> showAllTarif() throws ServiceException {
        List<Tarif> tarifs = new ArrayList<>();
        try {
            tarifs.addAll(tarifDAO.getAll());
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("problems with dao");
        }
        return tarifs;
    }

    @Override
    public List<Tarif> showTariffRange(int page, int limit) throws ServiceException {
        List<Tarif> tariffs;
        try {
            logger.info("TARIFF DAO "+tarifDAO);
            tariffs = tarifDAO.getTariffRange(page, limit);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("problems with dao");
        }
        return tariffs;
    }

    @Override
    public Tarif showTarifById(int id) throws ServiceException {
        Tarif tarif;
        try {
            tarif = tarifDAO.getTarifById(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't get tarifs by id");
        }
        return tarif;
    }

    @Override
    public boolean addTarif(Tarif tarif) throws ServiceException {
        boolean flag;
        if (!validator.nameDescripValidate(tarif.getName())) {
            throw new ServiceException("Invalid name");
        }
        if (!validator.nameDescripValidate(tarif.getDescription())) {
            throw new ServiceException("Invalid description");
        }
        if (!validator.discountValidate(tarif.getDiscount())) {
            throw new ServiceException("Invalid discount");
        }
        if (!validator.priceValidate(tarif.getPrice())) {
            throw new ServiceException("Invalid price");
        }
        if (!validator.speedValidate(tarif.getSpeed())) {
            throw new ServiceException("Invalid speed");
        }
        try {
            tarifDAO.addTarif(tarif);
            flag = true;
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't add new tariff");
        }

        return flag;
    }

    @Override
    public boolean deleteTarif(int id) throws ServiceException {
        try {
            return tarifDAO.deleteTarifById(id);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("CAn't delete tarif");
        }

    }

    @Override
    public boolean changeTarif(Tarif tarif) throws ServiceException {
        boolean flag;
        if (!validator.nameDescripValidate(tarif.getName())) {
            throw new ServiceException("Invalid name");
        }
        if (!validator.nameDescripValidate(tarif.getDescription())) {
            throw new ServiceException("Invalid discription");
        }
        if (!validator.discountValidate(tarif.getDiscount())) {
            throw new ServiceException("Invalid discount");
        }
        if (!validator.priceValidate(tarif.getPrice())) {
            throw new ServiceException("Invalid price");
        }
        if (!validator.speedValidate(tarif.getSpeed())) {
            throw new ServiceException("Invalid speed");
        }
        try {
            flag = tarifDAO.editTarif(tarif);

        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException("Can't change tarif");
        }
        return flag;
    }

    @Override
    public List<UserTarif> showTarifsByUserId(int id) throws ServiceException {
        List<UserTarif> tariffs;
        try {
            tariffs = tarifDAO.getTarifByUserId(id);
        } catch (DAOException e) {
            logger.error("Can't get tariffs list by user id from dao ");
            throw new ServiceException(e);
        }
        return tariffs;
    }
}
