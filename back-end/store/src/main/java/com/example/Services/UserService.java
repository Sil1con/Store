package com.example.Services;

import com.example.Entities.User;
import com.example.Repositories.UserRepository;
import com.example.Services.DAOs.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class UserService implements UserDAO {
    private final UserRepository userRepository = new UserRepository();

    @Override
    public void save(User user) throws SQLException{
        userRepository.save(user);
    }
    @Override
    public void update(User user) throws SQLException {
        userRepository.update(user);
    }
    @Override
    public void delete(int id) throws SQLException {
        userRepository.delete(id);
    }
    @Override
    public User findById(int id) throws SQLException {
        if (userRepository.findById(id) == null) {
            return null;
        } else {
            return userRepository.findById(id);
        }
    }
    @Override
    public User findByEmail(String email) throws SQLException {
        if (userRepository.findByEmail(email) == null) {
            return null;
        } else {
            return userRepository.findByEmail(email);
        }
    }
    @Override
    public User findByUserName(String name) throws SQLException {
        if (userRepository.findByUserName(name) == null){
            return null;
        } else {
            return userRepository.findByUserName(name);
        }
    }
    @Override
    public List<User> findAllUsers() throws SQLException {
        return userRepository.findAllUsers();
    }

    @Override
    public void saveKey(int user_id,String api_key) throws SQLException {
        userRepository.saveKey(user_id, api_key);
    }

    @Override
    public int findByKey(String api_key) throws SQLException {
        return userRepository.findByKey(api_key);
    }
}
