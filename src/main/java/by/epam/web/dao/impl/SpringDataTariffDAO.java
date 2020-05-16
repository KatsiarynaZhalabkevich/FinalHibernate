package by.epam.web.dao.impl;

import by.epam.web.bean.Note;
import by.epam.web.bean.Tarif;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dto.UserTarif;
import by.epam.web.repository.TariffRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class SpringDataTariffDAO  implements TarifDAO {

    @Autowired
    private TariffRepository tariffRepository;

    @PersistenceContext //для метода, который работает через hibernate
   private EntityManager em;

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

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Tuple> criteria = criteriaBuilder.createQuery(Tuple.class);
            Root<Tarif> tarif = criteria.from(Tarif.class);

            Join<Tarif, Note> noteJoin = tarif.join("notes", JoinType.INNER);

            Predicate eq2 = criteriaBuilder.equal(noteJoin.get("user").get("id"), id);
            criteria.multiselect(tarif.get("id").alias("tarifId"),
                    tarif.get("name"),
                    tarif.get("description"),
                    tarif.get("speed"),
                    tarif.get("price"),
                    tarif.get("discount"),
                    noteJoin.get("time"), noteJoin.get("id"), noteJoin.get("user").get("id"))
                    .where(eq2);

            List<UserTarif> userTarifs = new ArrayList<>();
            List<Tuple> tupleList = em.createQuery(criteria).getResultList();

            tupleList.forEach(t -> {

                int tarifId = (int) t.get("tarifId");
                String name = (String) t.get("name");
                String descr = (String) t.get("description");
                int speed = (int) t.get("speed");
                double price = (double) t.get("price");
                double discount = (double) t.get("discount");
                Date date = (Date) t.get(6);
                int noteId = (int) t.get(7);
                int userId1 = (int) t.get(8);

                Tarif tarif1 = new Tarif();

                tarif1.setId(tarifId);
                tarif1.setName(name);
                tarif1.setDescription(descr);
                tarif1.setPrice(price);
                tarif1.setDiscount(discount);
                tarif1.setSpeed(speed);

                UserTarif userTarif = new UserTarif();
                userTarif.setTarif(tarif1);
                userTarif.setDate(date);
                userTarif.setNoteId(noteId);
                userTarif.setUserId(userId1);

                userTarifs.add(userTarif);
            });

            return userTarifs;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }

    }
//проверить!!!!
    @Override
    public List<Tarif> getTariffRange(int page, int limit) throws DAOException {
        List<Tarif> tarifsOnPage;
        Page<Tarif> all = tariffRepository.findAll( PageRequest.of(page, limit));
        tarifsOnPage = all.getContent();
        return tarifsOnPage;
    }
}
