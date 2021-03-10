/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.interfaces.repo;

import com.anhnd.entity.Category;
import java.util.List;

/**
 *
 * @author anhnd
 */
public interface ICategoryRepository {
    
    public List<Category> getAllCategory();
    
    public boolean insertCategory(Category category);
    
    public Category findCategoryByID(String catecoryID);
    
    public boolean editCategory(Category category);
    
    public boolean deleteCategory(String categoryID);
    
}
