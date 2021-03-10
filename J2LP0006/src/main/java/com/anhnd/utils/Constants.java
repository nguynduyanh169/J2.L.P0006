/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhnd.utils;

/**
 *
 * @author anhnd
 */
public class Constants {
    public static final String IP = "10.1.85.114";
    public static final String USER_URL = "rmi://" + IP + ":6789/UserRMI";
    public static final String CATEGORY_URL = "rmi://" + IP + ":6789/CategoryURL";
    public static final String REGISTRATIONID_REGEX = "^[a-zA-Z0-9 ]+$";
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String NUMBER_REGEX = "^[0-9]+$";
    public static final String AGE_REGEX = "^0*(?:[1-9][0-9]?|100)$";
}
