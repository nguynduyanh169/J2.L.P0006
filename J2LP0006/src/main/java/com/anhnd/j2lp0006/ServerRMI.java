/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.j2lp0006;

import com.anhnd.impls.rmi.CategoryRMI;
import com.anhnd.impls.rmi.ProductRMI;
import com.anhnd.impls.rmi.UserRMI;
import com.anhnd.interfaces.rmi.ICategoryRMI;
import com.anhnd.interfaces.rmi.IProductRMI;
import com.anhnd.interfaces.rmi.IUserRMI;
import com.anhnd.utils.Constants;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author anhnd
 */
public class ServerRMI {
    public static void main(String[] args) {
        try {
            IUserRMI userRMI = new UserRMI();
            ICategoryRMI categoryRMI = new CategoryRMI();
            IProductRMI productRMI = new ProductRMI();
            LocateRegistry.createRegistry(6789);
            Naming.bind(Constants.USER_URL, userRMI);
            Naming.bind(Constants.CATEGORY_URL, categoryRMI);
            Naming.bind(Constants.PRODUCT_URL, productRMI);
            System.out.println("Server running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
