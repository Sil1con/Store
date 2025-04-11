package com.example.Repositories;

import com.example.Entities.Admin;
import com.example.Entities.SuperAdmin;
import com.example.Entities.User;
import com.example.Repositories.DAOs.UserRepositoryDAO;
import com.example.Role.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository implements UserRepositoryDAO {
    private Connection conn = null;
    private static final String URL = "jdbc:mysql://localhost:3307/project";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "4321";

    private void setConnection(){
        try{
            conn = DriverManager
                    .getConnection(URL, DB_USER, DB_PASS);
        } catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void save(User user) throws SQLException{
        setConnection();
        PreparedStatement ps;

        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        int privilege_id = 0;

        switch(user.getRole()){ //to set the privilege_id
            case Role.CUSTOMER:
                privilege_id = 3;
                break;
            case Role.ADMIN:
                privilege_id = 2;
                break;
            case Role.SUPER_ADMIN:
                privilege_id = 1;
                break;
        }
        ps = conn.prepareStatement(sql1);
        ps.setNull(1, 0);
        ps.setString(2, user.getFname());
        ps.setString(3, user.getLname());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getPassword());
        ps.setInt(7, privilege_id);
        ps.setString(8, user.getSalt());
        ps.executeUpdate();
        ps.close();
    }
    public void update(User user) throws SQLException { //
        setConnection();
        PreparedStatement ps = null;

        String sql = "CALL updateUser(?,?,?,?,?,?,?,?)";
        int privilege_id = 0;

        switch(user.getRole()){ //to set the privilege_id
            case Role.CUSTOMER:
                privilege_id = 3;
                break;
            case Role.ADMIN:
                privilege_id = 2;
                break;
            case Role.SUPER_ADMIN:
                privilege_id = 1;
                break;
        }

        ps = conn.prepareStatement(sql);
        ps.setInt(1, user.getId());
        ps.setString(2, user.getFname());
        ps.setString(3, user.getLname());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getPassword());
        ps.setInt(7, privilege_id);
        ps.setString(8, user.getSalt());
        ps.executeUpdate();

    }

    public void delete(int id) throws SQLException { //return sth if id doesn't exist
        setConnection();
        Statement stmt = null;
        String sql = "DELETE FROM users WHERE user_id = " + id;

        stmt = conn.createStatement();
        stmt.executeUpdate(sql);

    }

    public User findById(int id) throws SQLException {
        setConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";

        User user = null;

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        } else {
            switch (rs.getInt(7)) {
                case 3: //Role.CUSTOMER
                    user = new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
                case 2: //Role.ADMIN
                    user = new Admin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
                case 1: //Role.SUPER_ADMIN
                    user = new SuperAdmin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
            }
        }
        return user;
    }
    public User findByEmail(String email) throws SQLException {
        setConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;

        stmt = conn.prepareStatement(sql);
        stmt.setString(1,email);
        rs = stmt.executeQuery();

        if (!rs.next()) {
            return user;
        } else {
            switch (rs.getInt(7)) {
                case 3: //Role.CUSTOMER
                    user = new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
                case 2: //Role.ADMIN
                    user = new Admin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
                case 1: //Role.SUPER_ADMIN
                    user = new SuperAdmin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
            }
        }
        return user;
    }
    public User findByUserName(String name) throws SQLException {
        setConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE fname LIKE ? OR lname LIKE ?";

        User user = null;

        stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, name);
        rs = stmt.executeQuery();

        if (!rs.next()) {
            return user;
        } else {
            switch (rs.getInt(7)) {
                case 3: //Role.CUSTOMER
                    user = new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
                case 2: //Role.ADMIN
                    user = new Admin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
                case 1: //Role.SUPER_ADMIN
                    user = new SuperAdmin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    break;
            }
        }
        return user;
    }

    public List<User> findAllUsers() throws SQLException{
        setConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users ORDER BY user_id DESC";

        User user;
        List<User> users = new ArrayList<User>() {
        };

        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            switch (rs.getInt(7)) {
                case 3: //Role.CUSTOMER
                    user = new User(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    users.add(user);
                    break;
                case 2: //Role.ADMIN
                    user = new Admin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    users.add(user);
                    break;
                case 1: //Role.SUPER_ADMIN
                    user = new SuperAdmin(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(8));
                    users.add(user);
                    break;
            }
        }
        return users;
    }

    public int findByKey(String api_key) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int user_id = 0;
        String sql = "SELECT * FROM api_keys WHERE api_key = ?";

        ps = conn.prepareStatement(sql);
        ps.setString(1, api_key);
        rs = ps.executeQuery();

        if (!rs.next()) {
            return 0;
        } else {
            user_id = rs.getInt(1);
        }
        return user_id;
    }

    public void saveKey(int user_id, String api_key) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO api_keys VALUES (?,?)";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, user_id);
        ps.setString(2, api_key);
        ps.executeUpdate();
        ps.close();
    }
}