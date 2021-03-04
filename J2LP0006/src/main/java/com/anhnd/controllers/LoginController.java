/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.controllers;

import com.anhnd.interfaces.rmi.IUserRMI;
import com.anhnd.utils.Constants;
import com.anhnd.views.Login;
import com.anhnd.views.ProductManagementView;
import java.rmi.Naming;
import javax.swing.JOptionPane;

/**
 *
 * @author anhnd
 */
public class LoginController {
    private IUserRMI userRMI;
    private Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
    }
    
    public void init(){
        loginView.getBtnLogin().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogin(evt);
            }
        });
        
        loginView.getBtnClear().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClear(evt);
            }
        });
        
        loginView.setVisible(true);
    }
    
    public void buttonClear(java.awt.event.ActionEvent evt){
        loginView.getTxtPassword().setText("");
        loginView.getTxtUsername().setText("");
    }
    
    public void buttonLogin(java.awt.event.ActionEvent evt){
        String username = loginView.getTxtUsername().getText();
        String password = loginView.getTxtPassword().getText();
        try {
            userRMI = (IUserRMI) Naming.lookup(Constants.USER_URL);
            boolean check = userRMI.checkLogin(username, password);
            if(check == false){
                JOptionPane.showMessageDialog(loginView, "Invalid username or password");
            }else{
                new ProductManagementView().setVisible(true);
                loginView.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
