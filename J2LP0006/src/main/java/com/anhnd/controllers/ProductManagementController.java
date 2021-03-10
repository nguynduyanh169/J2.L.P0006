/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.controllers;

import com.anhnd.entity.Category;
import com.anhnd.interfaces.rmi.ICategoryRMI;
import com.anhnd.utils.Constants;
import com.anhnd.views.ProductManagementView;
import java.rmi.Naming;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anhnd
 */
public class ProductManagementController {

    private ICategoryRMI categoryRMI;
    private ProductManagementView productManagementView;
    private DefaultTableModel categoryModel;
    private boolean isNewCategory = true;

    public ProductManagementController(ProductManagementView productManagementView) {
        this.productManagementView = productManagementView;
    }

    public void init() {
        categoryModel = (DefaultTableModel) productManagementView.getTblCategory().getModel();
        productManagementView.getBtnNewCategory().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewCategory(evt);
            }
        });
        productManagementView.getBtnSaveCategory().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveCategory(evt);
            }
        });
        productManagementView.getTblCategory().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseItemOnCategoryTable(evt);
            }
        });
        productManagementView.getBtnDeleteCategory().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteCategory(evt);
            }
        });
        getAllCategory();
        productManagementView.setVisible(true);
    }

    public void getAllCategory() {
        try {
            productManagementView.getCbCategoryName().removeAllItems();
            categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
            List<Category> categories = categoryRMI.getAllCategory();
            categoryModel.setRowCount(0);
            for (Category category : categories) {
                categoryModel.addRow(category.toVector());
                productManagementView.getCbCategoryName().addItem(category.getCategoryName());
            }
            productManagementView.getTblCategory().updateUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }
    }

    public void buttonNewCategory(java.awt.event.ActionEvent evt) {
        isNewCategory = true;
        productManagementView.getTxtCategoryID().setText("");
        productManagementView.getTxtCategoryID().setEditable(true);
        productManagementView.getTxtCategoryName().setText("");
        productManagementView.getTxtCategoryDescription().setText("");
    }

    public void buttonSaveCategory(java.awt.event.ActionEvent evt) {
        try {
            String categoryID = productManagementView.getTxtCategoryID().getText().trim();
            String categoryName = productManagementView.getTxtCategoryName().getText().trim();
            String description = productManagementView.getTxtCategoryDescription().getText().trim();
            Category category = new Category(categoryID, categoryName, description);
            if (isNewCategory == true) {
                categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
                boolean check = categoryRMI.insertCategory(category);
                if (check == true) {
                    productManagementView.getTxtCategoryID().setText("");
                    productManagementView.getTxtCategoryID().setEditable(true);
                    productManagementView.getTxtCategoryName().setText("");
                    productManagementView.getTxtCategoryDescription().setText("");
                    getAllCategory();
                }
                else{
                    JOptionPane.showMessageDialog(productManagementView, "Insert Failed!");
                }
            }else{
                categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
                boolean check = categoryRMI.editCategory(category);
                if(check == true){
                    productManagementView.getTxtCategoryID().setText("");
                    productManagementView.getTxtCategoryID().setEditable(true);
                    productManagementView.getTxtCategoryName().setText("");
                    productManagementView.getTxtCategoryDescription().setText("");
                    getAllCategory();
                }else{
                    JOptionPane.showMessageDialog(productManagementView, "Edit Failed!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }

    }
    
    public void chooseItemOnCategoryTable(java.awt.event.MouseEvent evt){
        try {
            isNewCategory = false;
            int pos = productManagementView.getTblCategory().getSelectedRow();
            String categoryID = (String) productManagementView.getTblCategory().getValueAt(pos, 0);
            categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
            Category category = categoryRMI.findCategoryByID(categoryID);
            if(category == null){
                JOptionPane.showMessageDialog(productManagementView, "Cannot find category: " + categoryID);
            }else{
                productManagementView.getTxtCategoryID().setText(categoryID);
                productManagementView.getTxtCategoryName().setText(category.getCategoryName());
                productManagementView.getTxtCategoryDescription().setText(category.getDescription());
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }
    }
    
    public void buttonDeleteCategory(java.awt.event.ActionEvent evt){
        try {
            int pos = productManagementView.getTblCategory().getSelectedRow();
            String categoryID = (String) productManagementView.getTblCategory().getValueAt(pos, 0);
            int confirm = JOptionPane.showConfirmDialog(productManagementView, "Do you want to delete " + categoryID + " ?", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
                boolean check = categoryRMI.deleteCategory(categoryID);
                if(check == true){
                    productManagementView.getTxtCategoryID().setText("");
                    productManagementView.getTxtCategoryID().setEditable(true);
                    productManagementView.getTxtCategoryName().setText("");
                    productManagementView.getTxtCategoryDescription().setText("");
                    getAllCategory();
                }else{
                    JOptionPane.showMessageDialog(productManagementView, "Delete failed!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }
    }
    
    

}
