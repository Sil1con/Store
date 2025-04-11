package com.example.Entities;

import java.sql.Blob;
import java.util.ArrayList;

public class Product {
    private int id;
    private String title;
    private String name;
    private String details;
    private String description;
    private String readme;
    private int amount;
    private float price;
    private String image;
    private ArrayList<Category> categories;

    public Product(int id, String title, String name, String details, String description, String readme,
                   int amount, float price, String image, ArrayList<Category> categories)
    {
        this.id = id;
        this.title = title;
        this.name = name;
        this.details = details;
        this.description = description;
        this.readme = readme;
        this.amount = amount;
        this.price = price;
        this.image = image;
        this.categories = categories;
    }
    public Product(String title, String name, String details, String description, String readme,
                   int amount, float price, String image, ArrayList<Category> categories){
        this.title = title;
        this.name = name;
        this.details = details;
        this.description = description;
        this.readme = readme;
        this.amount = amount;
        this.price = price;
        this.image = image;
        this.categories = categories;
    }
    //The product constructor below is used to create products that are presented in user's cart.
    public Product(int id, String title, String description, int amount, float price, String image, ArrayList<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.image = image;
        this.categories = categories;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDetails(String details) {this.details = details;}
    public void setReadme(String readme) {this.readme = readme;}
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
    public int getId() {return id;}
    public String getName() {return name;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getDetails() {return details;}
    public String getReadme() {return readme;}
    public int getAmount() {return amount;}
    public float getPrice() {return price;}
    public String getImage() {return image;}
    public ArrayList<Category> getCategories() {return categories;}

    @Override
    public String toString(){
        return "Id = " + id + "\t\t" + "Name = " + name
                + "\t\t" + "Amount = " + amount + "\t\t" + "Category = " + categories;
    }
}