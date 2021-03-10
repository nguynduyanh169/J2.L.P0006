/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.repo;

import com.anhnd.entity.Category;
import com.anhnd.impls.dao.CategoryDAO;
import com.anhnd.interfaces.dao.ICategoryDAO;
import com.anhnd.interfaces.repo.ICategoryRepository;
import java.util.List;

/**
 *
 * @author anhnd
 */
public class CategoryRepository implements ICategoryRepository{
    
    private ICategoryDAO categoryDAO;

    @Override
    public List<Category> getAllCategory() {
        categoryDAO = new CategoryDAO();
        return categoryDAO.getAllCategory();
    }

    @Override
    public boolean insertCategory(Category category) {
        categoryDAO = new CategoryDAO();
        return categoryDAO.insertCategory(category);
    }

    @Override
    public Category findCategoryByID(String categoryID) {
        categoryDAO = new CategoryDAO();
        return categoryDAO.findCategoryByID(categoryID);
    }

    @Override
    public boolean editCategory(Category category) {
        categoryDAO = new CategoryDAO();
        return categoryDAO.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(String categoryID) {
        categoryDAO = new CategoryDAO();
        return categoryDAO.deleteCategory(categoryID);
    }
    
    
}
