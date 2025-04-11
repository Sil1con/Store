package com.example.Repositories;

import com.example.Audit.*;
import com.example.Entities.Category;
import com.example.Entities.Product;
import com.example.Repositories.DAOs.ProductRepositoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements ProductRepositoryDAO {
    private static Connection conn = null;
    private final String url = "jdbc:mysql://localhost:3307/project";
    private final String DB_USER = "root";
    private final String DB_PASS = "4321";

    public void setConnection() {
        try {
            conn = DriverManager
                    .getConnection(url, DB_USER, DB_PASS);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public String add(Product product) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO products VALUES (?,?,?,?,?,?,?,?,?)";
        String answer = "201_CREATED";

        if (findByName(product.getName()) != null) {

            answer = "The product is in the inventory";

        } else { //Create a new product in the storage
            ps = conn.prepareStatement(sql);
            ps.setNull(1, 0);
            ps.setString(2, product.getTitle());
            ps.setString(3, product.getName());
            ps.setString(4, product.getDetails());
            ps.setString(5, product.getDescription());
            ps.setString(6, product.getReadme());
            ps.setInt(7, product.getAmount());
            ps.setFloat(8, product.getPrice());
            ps.setString(9, product.getImage());

            if (Audit.categoryAudit(product).size() != 0) { //categoryAudit(product) returns mismatched categories
                for (Category obj : Audit.categoryAudit(product)){
                    answer = "There is no category: " + obj + " in the inventory";
                }
                ps.close();
            } else { //Insert the PRODUCT categories into products_categories table
                ps.executeUpdate();
                String insert = "INSERT INTO products_categories VALUES (?,?)";

                for (Category productCategory : product.getCategories()){
                    ps = conn.prepareStatement(insert);
                    ps.setInt(1, product.getId());
                    ps.setInt(2, productCategory.getId());
                    ps.executeUpdate();
                }
            }
        }
        return answer;
    };
    public String delete(int id) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM products WHERE id = ?";
        String answer = null;

        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

        answer = "200_OK";
        return answer;
    };
    public String edit(Product updatedProduct) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        String answer = null;

        int oldProductId = updatedProduct.getId();
        updatedProduct = Audit.productAudit(findById(oldProductId), updatedProduct);

        if (updatedProduct.getTitle() != null) {
            String sql = "UPDATE products SET title = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.getTitle());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getName() != null) {
            String sql = "UPDATE products SET name = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.getName());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getDetails() != null) {
            String sql = "UPDATE products SET details = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.getDetails());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getDescription() != null) {
            String sql = "UPDATE products SET description = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.getDescription());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getReadme() != null) {
            String sql = "UPDATE products SET readme = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.getName());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getAmount() != -1){
            String sql = "UPDATE products SET amount = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, updatedProduct.getAmount());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getPrice() != -1) {
            String sql = "UPDATE products SET price = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, updatedProduct.getPrice());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }
        if (updatedProduct.getImage() != null) {
            String sql = "UPDATE products SET image = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, updatedProduct.getImage());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }

        String delete = "DELETE FROM products_categories WHERE product_id = ?";
        String insert = "INSERT INTO products_categories VALUES (?, ?)";
        ps = conn.prepareStatement(delete);
        ps.setInt(1,oldProductId);
        ps.executeUpdate();
        for (Category obj : updatedProduct.getCategories()) {
            ps = conn.prepareStatement(insert);
            ps.setInt(1, oldProductId);
            ps.setInt(2, obj.getId());
            ps.executeUpdate();
        }
        if (updatedProduct.getId() != -1) {
            String sql = "UPDATE products SET product_id = ? WHERE product_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, updatedProduct.getId());
            ps.setInt(2, oldProductId);
            ps.executeUpdate();
        }

        ps.close();
        return answer; //If I have a problem with execution, it will return null;
    }
    public Product findById(int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products WHERE product_id = ?";

        Product product = null;
        ArrayList<Category> categories;

        ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            categories = new ArrayList<>();

            //findAllProductCategories(String product_name)
            for (Category obj : findAllProductCategories(rs.getString(2))) {
                categories.add(obj);
            }
            product = new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getFloat(8),
                    rs.getString(9),
                    categories
            );
        } else
            return null;

        return product;
    }

    public Product findByName(String name) throws SQLException {
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products WHERE name = ?";

        Product product = null;
        ArrayList<Category> categories;


        ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        rs = ps.executeQuery();

        if (rs.next()){
            categories = new ArrayList<>();

            for (Category obj : findAllProductCategories(name)) {
                categories.add(obj);
            }
            product = new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getFloat(8),
                    rs.getString(9),
                    categories
            );
        } else
            return null;
        return product;
    }
    public List<Product> findAllByDescOrder() throws SQLException{
        setConnection();
        Statement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM products ORDER BY amount DESC";

        Product product = null;
        ArrayList<Category> categories;
        List<Product> products = null;

        st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = st.executeQuery(sql);

        products = new ArrayList<>();

        while (rs.next()) {
            categories = new ArrayList<>();

            //findAllProductCategories(String product_name)
            for (Category obj : findAllProductCategories(rs.getString(2)) ) {
                categories.add(obj);
            }

            product = new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getFloat(8),
                    rs.getString(9),
                    categories
            );
            products.add(product);
        }
        return products;
    };
    public List<Product> findAllByCategory(String category) throws SQLException{
        setConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL findAllByCategory (?)";

        List<Product> products = null;
        ArrayList<Category> categories;
        Product product;


        ps = conn.prepareStatement(sql);
        ps.setString(1, category);
        rs = ps.executeQuery();

        products = new ArrayList<>();

        while (rs.next()) {
            categories = new ArrayList<>();
            for (Category obj : findAllProductCategories(rs.getString(2))) {
                categories.add(obj);
            }
            product = new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getFloat(8),
                    rs.getString(9),
                    categories
            );
            products.add(product);
        }

        return products;
    }
    private List<Category> findAllProductCategories(String name) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "CALL findAllProductCategories (?)";

        Category category;
        List<Category> categories = new ArrayList<Category>();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            rs = ps.executeQuery();

            while (rs.next()) {
                category = new Category(
                        rs.getInt(2),
                        rs.getString(3)
                );
                categories.add(category);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return categories;
    }
}
