package com.example.Entities;

public class Category {
    private int id;
    private String category;

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getId() {
        return id;
    }
    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "{ID = " + id + ",Category = " + category + "}";
    }
}
