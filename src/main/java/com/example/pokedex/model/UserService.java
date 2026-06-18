package com.example.pokedex.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserDAO userDAO;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public void registrarUsuario(User user) {
        // Criptografa a senha antes de salvar
        String senhaEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaEncriptada);
        userDAO.inserirUsuario(user);
    }
    
    public User autenticar(String username, String password) {
        User user = userDAO.buscarPorUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    public User buscarPorId(String id) {
        return userDAO.buscarPorId(id);
    }
}
