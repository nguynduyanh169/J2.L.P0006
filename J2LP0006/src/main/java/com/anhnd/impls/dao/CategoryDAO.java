/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.dao;

import com.anhnd.entity.Category;
import com.anhnd.interfaces.dao.ICategoryDAO;
import com.anhnd.utils.HibernateUtils;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author anhnd
 */
public class CategoryDAO implements ICategoryDAO {

    private Session session;

    public CategoryDAO() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> result = null;
        try {
            session.getTransaction().begin();
            String hql = " from Category";
            Query query = session.createQuery(hql, Category.class);
            result = query.getResultList();
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insertCategory(Category category) {
        boolean check = false;
        try {
            session.getTransaction().begin();
            check = session.save(category) == null ? false : true;
            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public Category findCategoryByID(String categoryID) {
        Category category = null;
        try {
            session.getTransaction().begin();
            category = session.get(Category.class, categoryID);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean updateCategory(Category category) {
        boolean check = false;
        try {
            session.getTransaction().begin();
            String hql = "Update Category c set c.categoryName = :categoryName, c.description = :description where c.categoryID = :categoryID";
            Query query = session.createQuery(hql);
            query.setParameter("categoryName", category.getCategoryName());
            query.setParameter("description", category.getDescription());
            query.setParameter("categoryID", category.getCategoryID());
            check = query.executeUpdate() > 0;
            session.flush();
            session.getTransaction().commit();

        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public boolean deleteCategory(String categoryID) {
        boolean check = false;
        try {
            session.getTransaction().begin();
            String hql = "Delete Category c where c.categoryID = :categoryID";
            Query query = session.createQuery(hql);
            query.setParameter("categoryID", categoryID);
            check = query.executeUpdate() > 0;
            session.flush();
            session.getTransaction().commit();
           
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return check;
    }
    
    

}
