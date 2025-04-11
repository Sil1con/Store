package com.example.Entities;

public class Order {
    private int id;
    private int user_id;
    private String fname;
    private String lname;
    private String phone;
    private String country;
    private String city;
    private String address;
    private String apartment;
    private String postal_code;
    private String company;
    private int payment_id;

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getApartment() {
        return apartment;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getCompany() {
        return company;
    }

    public int getPayment_id() {
        return payment_id;
    }
}
