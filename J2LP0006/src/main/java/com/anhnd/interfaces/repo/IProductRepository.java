/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.interfaces.repo;

import com.anhnd.entity.Product;
import java.util.List;

/**
 *
 * @author anhnd
 */
public interface IProductRepository {
    
    public List<Product> getAllProduct();
    
    public Product findProductByID(String productID);
    
    public boolean insertProduct(Product product);
    
    public boolean editProduct(Product product);
    
    public boolean deleteProduct(String productID);
    
}
