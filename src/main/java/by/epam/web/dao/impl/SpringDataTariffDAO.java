package by.epam.web.dao.impl;

import by.epam.web.bean.Tarif;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dto.UserTarif;
import by.epam.web.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpringDataTariffDAO implements TarifDAO {

    @Autowired
    private TariffRepository tariffRepository;

    @Override
    public boolean addTarif(Tarif tarif) throws DAOException {

        return tariffRepository.save(tarif) != null;
    }

    @Override
    public boolean editTarif(Tarif tarif) throws DAOException {
        Tarif updatedTarif = tariffRepository.saveAndFlush(tarif);
        return tarif.equals(updatedTarif);
    }

    @Override
    public Tarif getTarifById(int id) throws DAOException {
        return tariffRepository.getTarifById(id);
    }

    @Override
    public List<Tarif> getAll() throws DAOException {

        return tariffRepository.findAll();
    }

    @Override
    public boolean deleteTarifById(int id) throws DAOException {
        tariffRepository.deleteById(id);
        return !tariffRepository.existsById(id);
    }
//подумать
    @Override
    public List<UserTarif> getTarifByUserId(int id) throws DAOException {

        return null;
    }
//подумать
    @Override
    public List<Tarif> getTariffRange(int page, int limit) throws DAOException {
        return null;
    }
}
