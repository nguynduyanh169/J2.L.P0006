/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.controllers;

import com.anhnd.entity.Category;
import com.anhnd.entity.Product;
import com.anhnd.interfaces.rmi.ICategoryRMI;
import com.anhnd.interfaces.rmi.IProductRMI;
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
    private IProductRMI productRMI;
    private ProductManagementView productManagementView;
    private DefaultTableModel categoryModel;
    private DefaultTableModel productModel;
    private boolean isNewCategory = true;
    private boolean isNewProduct = true;

    public ProductManagementController(ProductManagementView productManagementView) {
        this.productManagementView = productManagementView;
    }

    public void init() {
        categoryModel = (DefaultTableModel) productManagementView.getTblCategory().getModel();
        productModel = (DefaultTableModel) productManagementView.getTblProduct().getModel();
        productManagementView.getBtnNewCategory().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewCategory(evt);
            }
        });
        productManagementView.getBtnNewProduct().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewProduct(evt);
            }
        });
        productManagementView.getBtnSaveCategory().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveCategory(evt);
            }
        });
        productManagementView.getBtnSaveProduct().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveProduct(evt);
            }
        });
        productManagementView.getTblCategory().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseItemOnCategoryTable(evt);
            }
        });
        productManagementView.getTblProduct().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseItemOnProductTable(evt);
            }
        });
        productManagementView.getBtnDeleteCategory().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteCategory(evt);
            }
        });
        productManagementView.getBtnDeleteProduct().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteProduct(evt);
            }
        });
        getAllCategory();
        getAllProduct();
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
                productManagementView.getCbCategoryName().addItem(category);
            }
            productManagementView.getTblCategory().updateUI();
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
        }
    }

    public void getAllProduct() {
        try {
            productRMI = (IProductRMI) Naming.lookup(Constants.PRODUCT_URL);
            List<Product> products = productRMI.getAllProduct();
            productModel.setRowCount(0);
            for (Product product : products) {
                System.out.printf("%.2f\n", product.getPrice());
                productModel.addRow(product.toVector());
            }
            productManagementView.getTblProduct().updateUI();
        } catch (Exception e) {
             if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
            
        }
    }

    public void buttonNewCategory(java.awt.event.ActionEvent evt) {
        isNewCategory = true;
        productManagementView.getTxtCategoryID().setText("");
        productManagementView.getTxtCategoryID().setEditable(true);
        productManagementView.getTxtCategoryName().setText("");
        productManagementView.getTxtCategoryDescription().setText("");
    }

    public void buttonNewProduct(java.awt.event.ActionEvent evt) {
        isNewProduct = true;
        productManagementView.getTxtProductID().setText("");
        productManagementView.getTxtProductID().setEditable(true);
        productManagementView.getTxtProductName().setText("");
        productManagementView.getTxtUnit().setText("");
        productManagementView.getTxtQuantity().setText("");
        productManagementView.getTxtPrice().setText("");
        productManagementView.getCbCategoryName().setSelectedIndex(0);

    }

    public void buttonSaveCategory(java.awt.event.ActionEvent evt) {
        try {
            String error = "";
            boolean isValid = true;
            String categoryID = productManagementView.getTxtCategoryID().getText().trim();
            String categoryName = productManagementView.getTxtCategoryName().getText().trim();
            String description = productManagementView.getTxtCategoryDescription().getText().trim();
            if (!categoryID.matches(Constants.ID_REGEX) || categoryID.isEmpty() || categoryID.length() > 10) {
                error += "\nCategoryID: maxlength is 10, not contains specific characters.";
                isValid = false;
            }
            if (categoryName.isEmpty() || categoryName.length() > 50) {
                error += "\nCategoryName: maxlength is 50.";
                isValid = false;
            }
            if (description.isEmpty() || description.length() > 200) {
                error += "\nDescription: maxlength is 200.";
                isValid = false;
            }
            if (isValid == false) {
                JOptionPane.showMessageDialog(productManagementView, error);
            } else {
                Category category = new Category(categoryID, categoryName, description);
                if (isNewCategory == true) {
                    categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
                    Category existCategory = categoryRMI.findCategoryByID(categoryID);
                    if (existCategory == null) {
                        boolean check = categoryRMI.insertCategory(category);
                        if (check == true) {
                            productManagementView.getTxtCategoryID().setText("");
                            productManagementView.getTxtCategoryID().setEditable(true);
                            productManagementView.getTxtCategoryName().setText("");
                            productManagementView.getTxtCategoryDescription().setText("");
                            getAllCategory();
                        } else {
                            JOptionPane.showMessageDialog(productManagementView, "Insert Failed!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(productManagementView, "Category " + categoryID + " has been exist!");
                    }
                } else {
                    categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
                    boolean check = categoryRMI.editCategory(category);
                    if (check == true) {
                        productManagementView.getTxtCategoryID().setText("");
                        productManagementView.getTxtCategoryID().setEditable(true);
                        productManagementView.getTxtCategoryName().setText("");
                        productManagementView.getTxtCategoryDescription().setText("");
                        getAllCategory();
                    } else {
                        JOptionPane.showMessageDialog(productManagementView, "Edit Failed!");
                    }
                }
            }
        } catch (Exception e) {
             if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
        }

    }

    public void buttonSaveProduct(java.awt.event.ActionEvent evt) {
        try {
            String error = "";
            boolean isValid = true;
            String productID = productManagementView.getTxtProductID().getText().trim();
            String productName = productManagementView.getTxtProductName().getText().trim();
            String unit = productManagementView.getTxtUnit().getText().trim();
            String priceText = productManagementView.getTxtPrice().getText().trim();
            String quantityText = productManagementView.getTxtQuantity().getText().trim();
            Category category = (Category) productManagementView.getCbCategoryName().getSelectedItem();
            if (!productID.matches(Constants.ID_REGEX) || productID.length() > 10 || productID.isEmpty()) {
                error += "\nProductID: maxlength is 10, not contains specific characters.";
                isValid = false;
            }
            if (productName.isEmpty() || productName.length() > 50) {
                error += "\nProductName: maxlength is 50.";
                isValid = false;
            }
            if (unit.length() > 50 || unit.isEmpty()) {
                error += "\nUnit: maxlength is 50.";
                isValid = false;
            }
            if (!priceText.matches(Constants.PRICE_REGEX)) {
                error += "\nPrice: must be a positive number.";
                isValid = false;
            }
            if (!quantityText.matches(Constants.QUANTITY_REGEX)) {
                error += "\nQuantity: must be a positive number.";
                isValid = false;
            }
            if (isValid == false) {
                JOptionPane.showMessageDialog(productManagementView, error);
            } else {
                int quantity = Integer.valueOf(quantityText);
                float price = Float.valueOf(priceText);
                Product product = new Product(productID, productName, unit, price, quantity, category);
                if (isNewProduct == true) {
                    productRMI = (IProductRMI) Naming.lookup(Constants.PRODUCT_URL);
                    Product existProduct = productRMI.findProductByID(productID);
                    if (existProduct == null) {
                        boolean check = productRMI.insertProduct(product);
                        if (check == true) {
                            getAllProduct();
                            productManagementView.getTxtProductID().setText("");
                            productManagementView.getTxtProductID().setEditable(true);
                            productManagementView.getTxtProductName().setText("");
                            productManagementView.getTxtUnit().setText("");
                            productManagementView.getTxtQuantity().setText("");
                            productManagementView.getTxtPrice().setText("");
                            productManagementView.getCbCategoryName().setSelectedIndex(0);
                        } else {
                            JOptionPane.showMessageDialog(productManagementView, "Insert Failed!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(productManagementView, "Product " + productID + " has been exist!");
                    }
                } else {
                    productRMI = (IProductRMI) Naming.lookup(Constants.PRODUCT_URL);
                    boolean check = productRMI.editProduct(product);
                    if (check == true) {
                        getAllProduct();
                        productManagementView.getTxtProductID().setText("");
                        productManagementView.getTxtProductID().setEditable(true);
                        productManagementView.getTxtProductName().setText("");
                        productManagementView.getTxtUnit().setText("");
                        productManagementView.getTxtQuantity().setText("");
                        productManagementView.getTxtPrice().setText("");
                        productManagementView.getCbCategoryName().setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(productManagementView, "Edit Failed!");
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }
    }

    public void chooseItemOnCategoryTable(java.awt.event.MouseEvent evt) {
        try {
            isNewCategory = false;
            int pos = productManagementView.getTblCategory().getSelectedRow();
            String categoryID = (String) productManagementView.getTblCategory().getValueAt(pos, 0);
            categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
            Category category = categoryRMI.findCategoryByID(categoryID);
            if (category == null) {
                JOptionPane.showMessageDialog(productManagementView, "Cannot find category: " + categoryID);
            } else {
                productManagementView.getTxtCategoryID().setText(categoryID);
                productManagementView.getTxtCategoryName().setText(category.getCategoryName());
                productManagementView.getTxtCategoryDescription().setText(category.getDescription());

            }
        } catch (Exception e) {
             if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
        }
    }

    public void chooseItemOnProductTable(java.awt.event.MouseEvent evt) {
        try {
            isNewProduct = false;
            int pos = productManagementView.getTblProduct().getSelectedRow();
            String productID = (String) productManagementView.getTblProduct().getValueAt(pos, 0);
            productRMI = (IProductRMI) Naming.lookup(Constants.PRODUCT_URL);
            Product product = productRMI.findProductByID(productID);
            if (product == null) {
                JOptionPane.showMessageDialog(productManagementView, "Cannot find product: " + productID);
            } else {
                productManagementView.getTxtProductID().setText(productID);
                productManagementView.getTxtProductID().setEditable(false);
                productManagementView.getTxtProductName().setText(product.getProductName());
                productManagementView.getTxtUnit().setText(product.getUnit());
                productManagementView.getTxtQuantity().setText(String.valueOf(product.getQuantity()));
                productManagementView.getTxtPrice().setText(String.format("%.1f", product.getPrice()));
                int cbSize = productManagementView.getCbCategoryName().getItemCount();
                for (int i = 0; i < cbSize; i++) {
                    Category item = productManagementView.getCbCategoryName().getItemAt(i);
                    if (item.getCategoryID().equals(product.getCategory().getCategoryID())) {
                        productManagementView.getCbCategoryName().setSelectedItem(item);
                    }
                }
            }
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
        }
    }

    public void buttonDeleteCategory(java.awt.event.ActionEvent evt) {
        try {
            int pos = productManagementView.getTblCategory().getSelectedRow();
            String categoryID = (String) productManagementView.getTblCategory().getValueAt(pos, 0);
            int confirm = JOptionPane.showConfirmDialog(productManagementView, "Do you want to delete " + categoryID + " ?", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                categoryRMI = (ICategoryRMI) Naming.lookup(Constants.CATEGORY_URL);
                boolean check = categoryRMI.deleteCategory(categoryID);
                if (check == true) {
                    productManagementView.getTxtCategoryID().setText("");
                    productManagementView.getTxtCategoryID().setEditable(true);
                    productManagementView.getTxtCategoryName().setText("");
                    productManagementView.getTxtCategoryDescription().setText("");
                    getAllCategory();
                } else {
                    JOptionPane.showMessageDialog(productManagementView, "Delete category: " + categoryID + " failed!");
                }
            }
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
        }
    }

    public void buttonDeleteProduct(java.awt.event.ActionEvent evt) {
        try {
            int pos = productManagementView.getTblProduct().getSelectedRow();
            String productID = (String) productManagementView.getTblProduct().getValueAt(pos, 0);
            int confirm = JOptionPane.showConfirmDialog(productManagementView, "Do you want to delete " + productID + " ?", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                productRMI = (IProductRMI) Naming.lookup(Constants.PRODUCT_URL);
                boolean check = productRMI.deleteProduct(productID);
                if (check == true) {
                    productManagementView.getTxtProductID().setText("");
                    productManagementView.getTxtProductID().setEditable(true);
                    productManagementView.getTxtProductName().setText("");
                    productManagementView.getTxtUnit().setText("");
                    productManagementView.getTxtQuantity().setText("");
                    productManagementView.getTxtPrice().setText("");
                    productManagementView.getCbCategoryName().setSelectedIndex(0);
                    getAllProduct();
                } else {
                    JOptionPane.showMessageDialog(productManagementView, "Delete product :" + productID + " failed!");
                }
            }
        } catch (Exception e) {
             if(e.getMessage().contains("Connection refused")){
                JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            }else{
                e.printStackTrace();
            }
        }
    }

}
