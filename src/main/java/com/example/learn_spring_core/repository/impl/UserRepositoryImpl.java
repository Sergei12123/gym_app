package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.entity.User;
import com.example.learn_spring_core.repository.BaseRepository;
import com.example.learn_spring_core.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.transaction.annotation.Transactional;


public abstract class UserRepositoryImpl<T extends User> extends BaseRepositoryImpl<T> implements UserRepository<T>, BaseRepository<T> {

    @Transactional
    public boolean existsByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            JpaRoot<T> root = criteriaQuery.from(getEntityClass());
            JpaPredicate condition = builder.equal(root.get("userName"), username);
            criteriaQuery.select(builder.count(root)).where(condition);
            return session.createQuery(criteriaQuery).uniqueResult() > 0;
        }

    }

}
