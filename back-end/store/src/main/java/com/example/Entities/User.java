package com.example.Entities;

import com.example.Role.Role;
public class User {
    private int id;
    private String name;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String password;
    private String salt;
    private final Role role = Role.CUSTOMER;

    public User(int id, String fname, String lname, String email, String phone, String password, String salt) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
        this.name = fname + " " + lname;
    }
    public User(String fname, String lname, String email, String phone, String password, String salt) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.name = fname + " " + lname;
        this.salt = salt;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {this.phone = phone;}
    public void setPassword(String password){
        this.password = password;
    }
    public void setSalt(String salt) {this.salt = salt;}
    //public void setRole(String role) {this.role = role;}
    public int getId() {
        return id;
    }
    public String getName() {return name;}
    public String getFname(){
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() {return phone;}
    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
    public String getSalt() {return salt;}
    @Override
    public String toString(){
        return "{ID = " + id + ", Name = " + fname + " " + lname + ", Email = " + email + ", Role = "  + role + "}";
    }
}
