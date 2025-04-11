package com.example.Entities;

import java.util.List;
public class Cart {
    private int cart_id;
    private int user_id;
    private List<Product> products;

    public Cart(int cart_id, int user_id, List<Product> products) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.products = products;
    }
    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public int getCart_id() {
        return cart_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart_id=" + cart_id +
                ", user_id=" + user_id +
                ", products=" + products +
                '}';
    }
}
