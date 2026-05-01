package com.example.pokedex.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserDAO userDAO;
    
    public void registrarUsuario(User user) {
        userDAO.inserirUsuario(user);
    }
    
    public User autenticar(String username, String password) {
        User user = userDAO.buscarPorUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public User buscarPorId(String id) {
        return userDAO.buscarPorId(id);
    }
}
