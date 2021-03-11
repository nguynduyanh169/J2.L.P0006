/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.repo;

import com.anhnd.entity.Product;
import com.anhnd.impls.dao.ProductDAO;
import com.anhnd.interfaces.dao.IProductDAO;
import com.anhnd.interfaces.repo.IProductRepository;
import java.util.List;

/**
 *
 * @author anhnd
 */
public class ProductRepository implements IProductRepository{
    
    private IProductDAO productDAO;

    @Override
    public List<Product> getAllProduct() {
        productDAO = new ProductDAO();
        return productDAO.getAllProduct();
    }

    @Override
    public Product findProductByID(String productID) {
        productDAO = new ProductDAO();
        return productDAO.findProductByID(productID);
    }

    @Override
    public boolean insertProduct(Product product) {
        productDAO = new ProductDAO();
        return productDAO.insertProduct(product);
    }

    @Override
    public boolean editProduct(Product product) {
        productDAO = new ProductDAO();
        return productDAO.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(String productID) {
        productDAO = new ProductDAO();
        return productDAO.deleteProduct(productID);
    }
    
}
