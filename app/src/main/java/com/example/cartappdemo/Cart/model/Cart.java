package com.example.cartappdemo.Cart.model;

import com.example.cartappdemo.Product.model.Product;

import java.util.ArrayList;

public class Cart {
//    ArrayList<ProductInCart> products;
//
//    public Cart() {
//    }
//
//    public Cart(ArrayList<ProductInCart> products) {
//        this.products = products;
//    }
//
//    public ArrayList<ProductInCart> getProducts() {
//        return products;
//    }
//
//    public void setProducts(ArrayList<ProductInCart> products) {
//        this.products = products;
//    }
//
//    //Nếu là sản phẩm mới
//    public boolean isAddToCartNewProduct(Product product) {
//        if (products == null) {
//            return false;
//        }
//        for (ProductInCart productInCart : products) {
//            if (productInCart.getId().equals(product.getId())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public long totalPrice() {
//        if (products == null) {
//            return 0;
//        }
//        long total = 0;
//        for (ProductInCart productInCart : products) {
//            total += productInCart.getPrice() * productInCart.getQuantity();
//        }
//        return total;
//    }
//
//    public long countProduct() {
//        if (products == null) {
//            return 0;
//        }
//        long count = 0;
//        for (ProductInCart productInCart : products) {
//            count += productInCart.getQuantity();
//        }
//        return count;
//    }
}
