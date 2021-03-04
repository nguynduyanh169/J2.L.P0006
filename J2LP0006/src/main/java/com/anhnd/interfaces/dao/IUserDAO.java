/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.interfaces.dao;

/**
 *
 * @author anhnd
 */
public interface IUserDAO {
    public boolean checkLogin(String username, String password);
}
