/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.impls.repo;

import com.anhnd.impls.dao.UserDAO;
import com.anhnd.interfaces.dao.IUserDAO;
import com.anhnd.interfaces.repo.IUserRepository;

/**
 *
 * @author anhnd
 */
public class UserRepository implements IUserRepository{
    
    private IUserDAO userDAO = new UserDAO();

    @Override
    public boolean checkLogin(String username, String password) {
        boolean check = userDAO.checkLogin(username, password);
        return check;
    }
    
}
