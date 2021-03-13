/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.dao;

import com.anhnd.entity.Product;
import com.anhnd.interfaces.dao.IProductDAO;
import com.anhnd.utils.HibernateUtils;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author anhnd
 */
public class ProductDAO implements IProductDAO{
    
    private Session session;

    public ProductDAO() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.getCurrentSession();
    }
    
    
    @Override
    public List<Product> getAllProduct() {
        List<Product> result = null;
        try {
            session.getTransaction().begin();
            String hql = " from Product";
            Query query = session.createQuery(hql, Product.class);
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
    public Product findProductByID(String productID) {
        Product product = null;
        try {
            session.getTransaction().begin();
            product = session.get(Product.class, productID);
            session.flush();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            return null;
        }
        return product;
    }

    @Override
    public boolean insertProduct(Product product) {
        boolean check = false;
        try {
            session.getTransaction().begin();
            check = session.save(product) == null ? false : true;
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
    public boolean updateProduct(Product product) {
        boolean check = false;
        try {
            session.getTransaction().begin();
            String hql = "Update Product p set p.productName = :productName, p.unit = :unit, "
                    + "p.price = :price, p.quantity = :quantity, p.category = :category where p.productID = :productID";
            Query query = session.createQuery(hql);
            query.setParameter("productName", product.getProductName());
            query.setParameter("unit", product.getUnit());
            query.setParameter("price", product.getPrice());
            query.setParameter("quantity", product.getQuantity());
            query.setParameter("category", product.getCategory());
            query.setParameter("productID", product.getProductID());
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
    public boolean deleteProduct(String productID) {
        boolean check = false;
        try {
            session.getTransaction().begin();
            String hql = "Delete from Product p where p.productID = :productID";
            Query query = session.createQuery(hql);
            query.setParameter("productID", productID);
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
