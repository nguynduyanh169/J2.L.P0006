/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.dao;

import com.anhnd.entity.User;
import com.anhnd.interfaces.dao.IUserDAO;
import com.anhnd.utils.HibernateUtils;
import java.util.ArrayList;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author anhnd
 */
public class UserDAO implements IUserDAO{

    private Session session;

    public UserDAO() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.getCurrentSession();
    }
    
    @Override
    public boolean checkLogin(String username, String password){
        boolean check = false;
        try {
            session.getTransaction().begin();
            String hql = " from User u where u.userID = :username and u.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            query.setParameter("password", password);
            User user = (User) query.getSingleResult();
            session.flush();
            session.getTransaction().commit();
            if(user != null){
                if(user.isStatus() == true){
                    check = true;
                }
            }
        } catch (Exception e) {
            if(session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return check;
    }

}
