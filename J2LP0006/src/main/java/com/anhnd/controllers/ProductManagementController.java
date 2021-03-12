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
import com.anhnd.utils.ComboItem;
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
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
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
                } else {
                    JOptionPane.showMessageDialog(productManagementView, "Insert Failed!");
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }

    }

    public void buttonSaveProduct(java.awt.event.ActionEvent evt) {
        try {
            String productID = productManagementView.getTxtProductID().getText().trim();
            String productName = productManagementView.getTxtProductName().getText().trim();
            String unit = productManagementView.getTxtUnit().getText().trim();
            String priceText = productManagementView.getTxtPrice().getText().trim();
            String quantityText = productManagementView.getTxtQuantity().getText().trim();

            int quantity = Integer.valueOf(quantityText);
            float price = Float.valueOf(priceText);
            Category category = (Category) productManagementView.getCbCategoryName().getSelectedItem();
            Product product = new Product(productID, productName, unit, price, quantity, category);
            if (isNewProduct == true) {
                productRMI = (IProductRMI) Naming.lookup(Constants.PRODUCT_URL);
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
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
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
                productManagementView.getTxtPrice().setText(String.valueOf(product.getPrice()));
                int cbSize = productManagementView.getCbCategoryName().getItemCount();
                for (int i = 0; i < cbSize; i++) {
                    Category item = productManagementView.getCbCategoryName().getItemAt(i);
                    if (item.getCategoryID().equals(product.getCategory().getCategoryID())) {
                        productManagementView.getCbCategoryName().setSelectedItem(item);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
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
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
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
                }else{
                     JOptionPane.showMessageDialog(productManagementView, "Delete product :" + productID + " failed!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productManagementView, "Failed to connect to server!");
            e.printStackTrace();
        }
    }

}
