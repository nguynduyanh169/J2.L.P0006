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
    public static final String IP = "192.168.1.3";
    public static final String USER_URL = "rmi://" + IP + ":6789/UserRMI";
    public static final String CATEGORY_URL = "rmi://" + IP + ":6789/CategoryURL";
    public static final String PRODUCT_URL = "rmi://" + IP + ":6789/ProductURL";
    public static final String ID_REGEX = "^[a-zA-Z0-9 ]+$";
    public static final String PRICE_REGEX = "^(\\d*\\.)?\\d+$";
    public static final String QUANTITY_REGEX = "^[0-9]\\d*$";
}
