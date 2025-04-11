package com.example.Audit;

import com.example.Entities.Category;
import com.example.Entities.Product;
import com.example.Services.CategoryService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public abstract class Audit {
    //Comparing product categories with static categories in the storage =>
    // => if there are no such categories it will return an error.
    public static List<Category> categoryAudit(Product product) throws SQLException {
        CategoryService categoryService = new CategoryService();
        List<Category> mismatchedCategories = new ArrayList<>();

        //Loop retrieves each PRODUCT CATEGORY to be compared
        for (Category productCategory : product.getCategories())
        {
            //Loop compares the product category with EXISTING categories
            for (Category category : categoryService.findAllCategories())
            {
                //If the product category != an existing category => It will be added in the list of mismatched categories
                if (!productCategory.equals(category.getCategory()))
                {
                    mismatchedCategories.add(category);
                }
            }
        }
        return mismatchedCategories;
    }

    public static Product productAudit(Product oldProduct, Product updatedProduct) {

        int i = -1;
        String s = null;
        float f = 0;

        if (oldProduct.getId() == updatedProduct.getId()) {
            updatedProduct.setId(i);
        }
        if (oldProduct.getTitle().equals(updatedProduct.getTitle())) {
            updatedProduct.setTitle(s);
        }
        if (oldProduct.getName().equals(updatedProduct.getName())) {
            updatedProduct.setName(s);
        }
        if (oldProduct.getDetails().equals(updatedProduct.getDetails())) {
            updatedProduct.setDetails(s);
        }
        if (oldProduct.getDescription().equals(updatedProduct.getDescription())) {
            updatedProduct.setDescription(s);
        }
        if (oldProduct.getReadme().equals(updatedProduct.getReadme())) {
            updatedProduct.setReadme(s);
        }
        if (oldProduct.getAmount() == updatedProduct.getAmount()) {
            updatedProduct.setAmount(i);
        }
        if (oldProduct.getPrice() == updatedProduct.getPrice()) {
            updatedProduct.setPrice(f);
        }
        if (oldProduct.getImage().equals(updatedProduct.getImage())) {
            updatedProduct.setImage(null);
        }
        return updatedProduct;
    }

    public static ArrayList<String> categoryJsonAudit(String categories) throws SQLException{
        CategoryService categoryService = new CategoryService();
        ArrayList<String> mismatchedCategories = new ArrayList<>();

        Scanner sc = new Scanner(categories);
        sc.useDelimiter(", ");

        while(sc.hasNext()) {
            String category = sc.next();
            for (Category loopCategory : categoryService.findAllCategories()) {
                if (!category.equals(loopCategory.getCategory())) {
                    mismatchedCategories.add(category);
                }
            }
        }
        if (!mismatchedCategories.isEmpty()) {
            return mismatchedCategories;
        } else {
            return null;
        }
    }
    public static ArrayList<Category> categoryJsonConverter(String categories) throws SQLException{
        CategoryService categoryService = new CategoryService();
        Scanner sc = new Scanner(categories);
        ArrayList<Category> convertedCategories = new ArrayList<>();

        while(sc.hasNext()) {
            String category = sc.next();
            for (Category loopCategory : categoryService.findAllCategories()) {
                if (category.equals(loopCategory.getCategory())) {
                    Category category1 = new Category(loopCategory.getId(), category);
                    convertedCategories.add(category1);
                }
            }
        }
        return convertedCategories;
    }
}
