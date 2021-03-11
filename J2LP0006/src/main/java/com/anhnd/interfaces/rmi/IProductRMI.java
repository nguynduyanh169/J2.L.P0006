/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.interfaces.rmi;

import com.anhnd.entity.Product;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author anhnd
 */
public interface IProductRMI extends Remote{
    
    public List<Product> getAllProduct() throws RemoteException;
    
    public Product findProductByID(String productID) throws RemoteException;
    
    public boolean insertProduct(Product product) throws RemoteException;
    
    public boolean editProduct(Product product) throws RemoteException;
    
    public boolean deleteProduct(String productID) throws RemoteException;
    
}
