package com.cyber.api.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class BaseService {

    @PersistenceContext
    EntityManager entityManager;

    protected Session getSession() {
        EntityManager manager = entityManager;
        try {
            return entityManager.unwrap(Session.class);
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            manager.close();
        }
    }

    public void persist(Object entity) {
        getSession().persist(entity);
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }

    public SQLQuery executeQuery(String sql) {
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query;
    }

}
