package com.example.Entities;

import com.example.Role.Role;
public class Admin extends User{
    private final Role role = Role.ADMIN;
    public Admin(int id, String fname, String lname, String email, String phone, String password, String salt){
        super(id, fname, lname, email, phone, password, salt);
    }
    public Admin(String fname, String lname, String email, String phone, String password, String salt){
        super(fname, lname, email, phone, password, salt);
    }
    @Override
    public Role getRole() {
        return role;
    }
    @Override
    public String toString(){
        return "{ID = " + getId() + ", Name = " + getFname() + " " + getLname() + ", Email = "
                + getEmail() + ", Password: " + getPassword() + ", Role = "  + role + "}";
    }
}
