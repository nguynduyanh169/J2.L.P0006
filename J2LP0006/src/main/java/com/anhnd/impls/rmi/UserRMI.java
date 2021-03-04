/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.rmi;

import com.anhnd.impls.repo.UserRepository;
import com.anhnd.interfaces.repo.IUserRepository;
import com.anhnd.interfaces.rmi.IUserRMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author anhnd
 */
public class UserRMI extends UnicastRemoteObject implements IUserRMI{
    
    private IUserRepository userRepository;

    public UserRMI() throws RemoteException{
        
    }

    @Override
    public boolean checkLogin(String username, String password) throws RemoteException {
        userRepository = new UserRepository();
        return userRepository.checkLogin(username, password);
    }
    
}
