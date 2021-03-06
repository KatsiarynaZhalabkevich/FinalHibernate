package by.epam.web.dao.impl;

import by.epam.web.bean.Note;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dto.UserTarif;
import by.epam.web.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Criteria JPA usage
 * HQL
 */

public class HibernateTariffDAO implements TarifDAO {
    @Override
    public boolean addTarif(Tarif tarif) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            session.persist(tarif);
            session.getTransaction().commit();
            session.clear();
            session.close();
            return true;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public boolean editTarif(Tarif tarif) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            session.update(tarif);
            session.getTransaction().commit();
            session.clear();
            session.close();
            return true;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }

    }

    @Override
    public Tarif getTarifById(int id) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tarif> criteria = criteriaBuilder.createQuery(Tarif.class);
            Root<Tarif> tarif = criteria.from(Tarif.class);
            criteria.select(tarif)
                    .where(criteriaBuilder.equal(tarif.get("id"), id));
            entityManager.close();
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (HibernateException e) {

            entityManager.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<Tarif> getAll() throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tarif> criteriaQuery = criteriaBuilder.createQuery(Tarif.class);
            Root<Tarif> tarif = criteriaQuery.from(Tarif.class);
            criteriaQuery.select(tarif);
            entityManager.close();
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            entityManager.close();
            throw new DAOException(e);
        }

    }

    @Override //+
    public boolean deleteTarifById(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("delete from Tarif where id = :id");
            query.setParameter("id", id);
            int count = query.executeUpdate();
            session.getTransaction().commit();
            session.clear();
            session.close();
            return count == 1;
        } catch (RollbackException e) {
            session.getTransaction().rollback();
            session.close();
            throw new DAOException(e);
        }


    }

    @Override
    public List<UserTarif> getTarifByUserId(int id) throws DAOException {

        EntityManager entityManager = HibernateUtil.getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

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
            List<Tuple> tupleList = entityManager.createQuery(criteria).getResultList();

            entityManager.close();
            tupleList.forEach(t -> {

                int tarifId = (int) t.get("tarifId");
                String name = (String) t.get(1);
                String descr = (String) t.get(2);
                int speed = (int) t.get(3);
                double price = (double) t.get(4);
                double discount = (double) t.get(5);
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

            entityManager.close();
            throw new DAOException(e);
        }

    }

    @Override
    public List<Tarif> getTariffRange(int page, int limit) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tarif> query = builder.createQuery(Tarif.class);
            query.select(query.from(Tarif.class));

            TypedQuery<Tarif> typedQuery = entityManager.createQuery(query);
            typedQuery.setFirstResult(page);
            typedQuery.setMaxResults(page * limit);

            return typedQuery.getResultList();

        } catch (HibernateException e) {

            entityManager.close();
            throw new DAOException(e);
        }

    }


}
