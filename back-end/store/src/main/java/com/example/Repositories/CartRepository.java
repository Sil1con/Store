package com.example.Repositories;

import com.example.Entities.Category;
import com.example.Entities.Product;
import com.example.Repositories.DAOs.CartRepositoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartRepository implements CartRepositoryDAO {
    private static Connection conn = null;
    private static final String URL = "jbdc:mysql://localhost:3307/project";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "4321";
    private static void setConnection() {
        try {
            conn = DriverManager
                    .getConnection(URL, DB_USER, DB_PASS);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    @Override
    public void add(int cart_id, int product_id, int product_amount, int user_id) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO carts VALUES (?,?,?,?)";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, cart_id);
        ps.setInt(2, product_id);
        ps.setInt(3, product_amount);
        ps.setInt(4, user_id);
        ps.executeUpdate();
    }
    @Override
    public void delete(int cart_id, int product_id) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM carts WHERE cart_id = ? AND product_id = ?";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, cart_id);
        ps.setInt(2, product_id);
        ps.executeUpdate();
    }

    @Override
    public void clean(int cart_id) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM carts WHERE cart_id = ?";
        String answer = "Problem with cart method delete()";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, cart_id);
        ps.executeUpdate();
    }

    @Override
    public List<Product> findAllProducts(int cart_id) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL findAllCartProducts(?)";

        List<Product> products = new ArrayList<>();
        ArrayList<Category> categories;
        Product product;

        ps = conn.prepareStatement(sql);
        ps.setInt(1, cart_id);
        rs = ps.executeQuery();

        if (!rs.next()) {
            return null;
        } else {
            categories = findAllProductCategories(rs.getString(3));
            product = new Product(
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getFloat(6),
                    rs.getString(7),
                    categories
            );
            products.add(product);
        }
        return products;
    }
    private ArrayList<Category> findAllProductCategories(String name) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL findAllProductCategories (?)";

        Category category;
        ArrayList<Category> categories = new ArrayList<Category>();

        ps = conn.prepareStatement(sql);
        ps.setString(1,name);
        rs = ps.executeQuery();

        if (!rs.next()) {
            return null;
        }
        while (rs.next()) {
            category = new Category(
                    rs.getInt(2),
                    rs.getString(3)
            );
            categories.add(category);
        }

        return categories;
    }
}
