/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.rmi;

import com.anhnd.entity.Product;
import com.anhnd.impls.repo.ProductRepository;
import com.anhnd.interfaces.repo.IProductRepository;
import com.anhnd.interfaces.rmi.IProductRMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author anhnd
 */
public class ProductRMI extends UnicastRemoteObject implements IProductRMI{
    
    private IProductRepository productRepository;
    
    public ProductRMI() throws RemoteException{
        
    }

    @Override
    public List<Product> getAllProduct() throws RemoteException {
        productRepository = new ProductRepository();
        return productRepository.getAllProduct();
    }

    @Override
    public Product findProductByID(String productID) throws RemoteException {
        productRepository = new ProductRepository();
        return productRepository.findProductByID(productID);
    }

    @Override
    public boolean insertProduct(Product product) throws RemoteException {
        productRepository = new ProductRepository();
        return productRepository.insertProduct(product);
    }

    @Override
    public boolean editProduct(Product product) throws RemoteException {
        productRepository = new ProductRepository();
        return productRepository.editProduct(product);
    }

    @Override
    public boolean deleteProduct(String productID) throws RemoteException {
        productRepository = new ProductRepository();
        return productRepository.deleteProduct(productID);
    }
    
}
