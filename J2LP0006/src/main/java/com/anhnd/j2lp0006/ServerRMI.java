/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.j2lp0006;

import com.anhnd.entity.User;
import com.anhnd.impls.dao.UserDAO;
import com.anhnd.impls.rmi.UserRMI;
import com.anhnd.interfaces.rmi.IUserRMI;
import com.anhnd.utils.Constants;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 *
 * @author anhnd
 */
public class ServerRMI {
    public static void main(String[] args) {
        try {
            IUserRMI userRMI = new UserRMI();
            LocateRegistry.createRegistry(6789);
            Naming.bind(Constants.USER_URL, userRMI);
            System.out.println("Server running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
