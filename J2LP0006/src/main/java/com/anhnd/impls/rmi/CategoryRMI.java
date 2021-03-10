/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.rmi;

import com.anhnd.entity.Category;
import com.anhnd.impls.repo.CategoryRepository;
import com.anhnd.interfaces.repo.ICategoryRepository;
import com.anhnd.interfaces.rmi.ICategoryRMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author anhnd
 */
public class CategoryRMI extends UnicastRemoteObject implements ICategoryRMI{
    
    private ICategoryRepository categoryRepository;
    
    public CategoryRMI() throws RemoteException{
        
    }

    @Override
    public List<Category> getAllCategory() throws RemoteException {
        categoryRepository = new CategoryRepository();
        return categoryRepository.getAllCategory();
    }

    @Override
    public boolean insertCategory(Category category) throws RemoteException {
        categoryRepository = new CategoryRepository();
        return categoryRepository.insertCategory(category);
    }

    @Override
    public Category findCategoryByID(String categoryID) throws RemoteException {
        categoryRepository = new CategoryRepository();
        return categoryRepository.findCategoryByID(categoryID);
    }

    @Override
    public boolean editCategory(Category category) throws RemoteException {
        categoryRepository = new CategoryRepository();
        return categoryRepository.editCategory(category);
    }

    @Override
    public boolean deleteCategory(String categoryID) throws RemoteException {
        categoryRepository = new CategoryRepository();
        return categoryRepository.deleteCategory(categoryID);
    }
    
}
