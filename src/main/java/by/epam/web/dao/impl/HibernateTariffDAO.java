package by.epam.web.dao.impl;

import by.epam.web.bean.Note;
import by.epam.web.bean.Tarif;
import by.epam.web.bean.User;
import by.epam.web.dao.DAOException;
import by.epam.web.dao.TarifDAO;
import by.epam.web.dto.UserTarif;
import by.epam.web.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Criteria JPA usage
 * HQL
 */

public class HibernateTariffDAO implements TarifDAO {
    @Override
    public boolean addTarif(Tarif tarif) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(tarif);
        session.getTransaction().commit();
        session.clear();
        session.close();
        return true;
    }

    @Override
    public boolean editTarif(Tarif tarif) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.update(tarif);
        session.getTransaction().commit();
        session.clear();
        session.close();
        return true;
    }

    @Override
    public Tarif getTarifById(int id) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tarif> criteria = criteriaBuilder.createQuery(Tarif.class);
        Root<Tarif> tarif = criteria.from(Tarif.class);
        criteria.select(tarif)
                .where(criteriaBuilder.equal(tarif.get("id"), id));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    @Override
    public List<Tarif> getAll() throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tarif> criteriaQuery = criteriaBuilder.createQuery(Tarif.class); //какой тип данных будет в результате
        Root<Tarif> tarif = criteriaQuery.from(Tarif.class); //с какой сущностью будем работать
        criteriaQuery.select(tarif);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public boolean deleteTarifById(int id) throws DAOException {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from Tarif where id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        session.getTransaction().commit();
        session.clear();
        session.close();
        return count == 1;

    }

    @Override //сделать через предикаты
    public List<UserTarif> getTarifByUserId(int id) throws DAOException {

        EntityManager entityManager = HibernateUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserTarif> criteria = criteriaBuilder.createQuery(UserTarif.class);
        Root<Tarif> tarif = criteria.from(Tarif.class);
        Root<Note> note = criteria.from(Note.class);
// "SELECT mytelecom.tarif.*, mytelecom.tarif_note.create_time,mytelecom.tarif_note.id AS 'noteId'
// FROM mytelecom.tarif, mytelecom.tarif_note
// WHERE mytelecom.tarif_note.tarif_id=mytelecom.tarif.id
// AND mytelecom.tarif_note.user_id=?;";

//        Join<User, Note> join = user.join("notes", JoinType.INNER);
//        criteria.where(criteriaBuilder.equal(tarif.get("id"), note.get("tarif")));
//        criteria.where(criteriaBuilder.equal(join.get("id"), id));
//        List<UserTarif> userTarifs = entityManager.createQuery(criteria).getResultList();

        Predicate eq1 = criteriaBuilder.equal(note.get("tariff"), tarif.get("id"));
        Predicate eq2 = criteriaBuilder.equal(note.get("user"), id);
        Predicate and = criteriaBuilder.and(eq1, eq2);

        criteria = criteria.multiselect(tarif, note.get("time"), note.get("id"))
                .where(and);
        List<UserTarif> userTarifs = entityManager.createQuery(criteria).getResultList();
        return userTarifs;
    }

    @Override
    public List<Tarif> getTariffRange(int page, int limit) throws DAOException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tarif> query = builder.createQuery(Tarif.class);
        query.select(query.from(Tarif.class));

        TypedQuery<Tarif> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(page);
        typedQuery.setMaxResults(page * limit);

        return typedQuery.getResultList();
    }


}
