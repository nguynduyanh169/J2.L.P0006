/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.interfaces.rmi;

import com.anhnd.entity.Category;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author anhnd
 */
public interface ICategoryRMI extends Remote{
    
    public List<Category> getAllCategory() throws RemoteException;
    
    public boolean insertCategory(Category category) throws RemoteException;
    
    public Category findCategoryByID(String categoryID) throws RemoteException;
    
    public boolean editCategory(Category category) throws RemoteException;
    
    public boolean deleteCategory(String categoryID) throws RemoteException;
}
