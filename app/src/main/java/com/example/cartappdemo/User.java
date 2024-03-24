package com.example.cartappdemo;


import com.example.cartappdemo.Cart.model.ProductInCart;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String username;
    private String timeCreateAccount;
    private String uid;
    private long totalPrice;

    private ArrayList<ProductInCart> cart;

    public String getTimeCreateAccount() {
        return timeCreateAccount;
    }

    public void setTimeCreateAccount(String timeCreateAccount) {
        this.timeCreateAccount = timeCreateAccount;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User() {
    }

    public User(String uid, String name, String email, String username, String timeCreateAccount) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.timeCreateAccount = timeCreateAccount;
        this.uid = uid;
    }

    public ArrayList<ProductInCart> getCarts() {
        return cart;
    }

    public void setCarts(ArrayList<ProductInCart> cart) {
        this.cart = cart;
    }

    public User(String uid, String name, String email, String username, String timeCreateAccount, long totalPrice) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.timeCreateAccount = timeCreateAccount;
        this.uid = uid;
        this.totalPrice = totalPrice;
    }

    public User(String uid, String name, String email, String timeCreateAccount) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.timeCreateAccount = timeCreateAccount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateAccount() {
        return timeCreateAccount;
    }

    public void setCreateAccount(String timeCreateAccount) {
        this.timeCreateAccount = timeCreateAccount;
    }

    public ArrayList<ProductInCart> getCart() {
        return cart;
    }

    public void setCart(ArrayList<ProductInCart> cart) {
        this.cart = cart;
    }
}
