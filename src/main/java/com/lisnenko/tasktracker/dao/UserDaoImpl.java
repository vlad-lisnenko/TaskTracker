package com.lisnenko.tasktracker.dao;

import com.lisnenko.tasktracker.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUserName(String userName) {

        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", userName);

        User user = null;
        try {
            user = theQuery.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        // create the user ... finally LOL
        entityManager.merge(theUser);
    }
}
