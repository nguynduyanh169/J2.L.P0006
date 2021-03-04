/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.interfaces.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author anhnd
 */
public interface IUserRMI extends Remote{
    
    public boolean checkLogin(String username, String password) throws RemoteException;
    
}
