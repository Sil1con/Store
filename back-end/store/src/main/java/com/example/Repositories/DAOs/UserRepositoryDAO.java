package com.example.Repositories.DAOs;

import com.example.Entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepositoryDAO {
    void save(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(int id) throws SQLException;
    User findById(int id) throws SQLException;
    User findByUserName(String name) throws SQLException;
    User findByEmail(String email) throws SQLException;
    List<User> findAllUsers() throws SQLException;
    void saveKey(int user_id, String api_key) throws SQLException;
    int findByKey(String api_key) throws SQLException;
}
