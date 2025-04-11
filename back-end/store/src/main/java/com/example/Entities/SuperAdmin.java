package com.example.Entities;

import com.example.Role.Role;
public class SuperAdmin extends Admin{
    private final Role role = Role.SUPER_ADMIN;
    public SuperAdmin(int id, String fname, String lname, String email, String phone, String password, String salt){
        super(id, fname, lname, email, phone, password, salt);
    }
    public SuperAdmin(String fname, String lname, String email, String phone,  String password, String salt){
        super(fname, lname, email, phone, password, salt);
    }
    @Override
    public Role getRole() {
        return role;
    }
    @Override
    public String toString(){
        return "{ID = " + getId() + ", Name = " + getFname() + " " + getLname() + ", Email = " + getEmail() + ", Role = "  + role + "}";
    }
}
