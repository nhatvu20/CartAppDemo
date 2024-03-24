package com.example.cartappdemo.Cart.model;

import com.example.cartappdemo.Product.model.Product;

public class ProductInCart extends Product {
    private int quantity;

    public ProductInCart() {
    }

    public ProductInCart(int quantity) {
        this.quantity = quantity;
    }

    public ProductInCart(String id, String name, String image, String description, long price, int quantity) {
        super(id, name, image, description, price);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductInCart{" +
                "quantity=" + quantity +
                '}';
    }
}
