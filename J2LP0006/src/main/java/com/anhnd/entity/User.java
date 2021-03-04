/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author anhnd
 */
@Entity
@Table(name = "Users")
public class User {
    
    @Id
    private String userID;
    
    @Column
    private String fullName;
    
    @Column
    private String password;
    
    @Column
    private boolean status;

    public User() {
    }

    public User(String userID, String fullName, String password, boolean status) {
        this.userID = userID;
        this.fullName = fullName;
        this.password = password;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", fullName=" + fullName + ", password=" + password + ", status=" + status + '}';
    }
    
    
}
