package com.video.dao.impl;

import com.video.dao.Dao;
import com.video.domains.SimpleEntity;
import com.video.utils.DG;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
public abstract class SimpleDao<E extends SimpleEntity> implements Dao<E> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<E> entityClass;

    public SimpleDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<E> getAll() {
        return entityManager.createQuery("select a from " + entityClass.getName() + " a", entityClass).getResultList();
    }

    @Override
    public void save(E obj) {
        entityManager.persist(obj);
    }

    @Override
    public void delete(E obj) {
        entityManager.remove(obj);
    }

    @Override
    public E get(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> get(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<E>();
        }
        return entityManager.createQuery("select e from " + entityClass.getName() + " e " +
                                         "  where e.id in (:ids)", entityClass)
                            .setParameter("ids", ids)
                            .getResultList();
    }

    @Override
    public void deleteByID(Long id) {
        if (DG.getLong(id) <= 0) {
            return;
        }
        E obj = get(id);
        delete(obj);
    }
}
