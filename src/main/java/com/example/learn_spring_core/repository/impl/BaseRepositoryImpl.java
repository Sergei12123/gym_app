package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.component.KeyHolderGenerator;
import com.example.learn_spring_core.entity.BaseEntity;
import com.example.learn_spring_core.exception.InitializeDbException;
import com.example.learn_spring_core.repository.BaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Repository
@Transactional(propagation = REQUIRES_NEW)
public abstract class BaseRepositoryImpl<T extends BaseEntity> implements BaseRepository<T> {
    protected KeyHolderGenerator keyHolderGenerator;

    @Autowired
    public void setKeyHolderGenerator(KeyHolderGenerator keyHolderGenerator) {
        this.keyHolderGenerator = keyHolderGenerator;
    }

    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void initializeDb(final String pathToScript) {
        try (Session session = sessionFactory.openSession()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToScript))) {
                StringBuilder script = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    script.append(line).append("\n");
                }
                Transaction transaction = session.beginTransaction();
                session.createNativeMutationQuery(script.toString()).executeUpdate();
                transaction.commit();
            } catch (IOException e) {
                throw new InitializeDbException(e);
            }
        }


    }

    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM " + getEntityClass().getName();
            Query query = session.createQuery(hql);
            return query.getResultList();
        }
    }

    @Transactional(propagation = REQUIRES_NEW)
    public T getById(final Long id) {
        return sessionFactory.getCurrentSession().get(getEntityClass(), id);
    }

    public void deleteById(final Long id) {
        final T entity = getById(id);
        sessionFactory.getCurrentSession().remove(entity);
    }

    public T save(T entity) {
        sessionFactory.getCurrentSession().persist(entity);
        return entity;
    }

    public void update(T entity) {
        sessionFactory.getCurrentSession().merge(entity);
    }
}
