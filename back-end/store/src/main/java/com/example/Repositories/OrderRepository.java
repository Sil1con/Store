package com.example.Repositories;

import com.example.Repositories.DAOs.OrderRepositoryDAO;
import com.example.Entities.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements OrderRepositoryDAO {
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
    public void save(Order order) throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO order_details VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, order.getId());
        ps.setInt(2, order.getUser_id());
        ps.setString(3, order.getFname());
        ps.setString(4, order.getLname());
        ps.setString(5, order.getPhone());
        ps.setString(6, order.getCountry());
        ps.setString(7, order.getCity());
        ps.setString(8, order.getAddress());
        ps.setString(9, order.getApartment());
        ps.setString(10, order.getPostal_code());
        ps.setString(11, order.getCompany());
        ps.setInt(12, order.getPayment_id());
        ps.setNull(13, 0);
        ps.executeUpdate();
    }
    @Override
    public void delete(int order_id) throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM order_details WHERE order_id = ?";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, order_id);
        ps.executeUpdate();
    }
    @Override
    public int revenueByMonth(int month) throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL soldProductsByMonth(?)";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, month);
        rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("revenue");
        } else
            return 0;
    }
    @Override
    public int soldProductsByMonth(int month) throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL soldProductsByMonth(?)";

        ps = conn.prepareStatement(sql);
        ps.setInt(1,month);
        rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("amount");
        } else
            return 0;
    }
    @Override
    public List<Integer> mostSoldProductsByMonth(int month) throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL mostSoldProductsByMonth";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, month);
        rs = ps.executeQuery();

        if (rs.next()) {
            ArrayList<Integer> products_id = new ArrayList<>();
            while (rs.next()) {
                products_id.add(rs.getInt("product_id"));
            }
            return products_id;
        } else
            return null;
    }
    @Override
    public int avrOrderValue() throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL avrOrderValue()";

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("average");
        } else
            return 0;
    }

    @Override
    public int allCustomersByMonth(int month) throws SQLException {
        return 0;
    }
}
