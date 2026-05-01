package com.example.pokedex.model;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class UserDAO {
    
    @Autowired
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void initialize() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void inserirUsuario(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }
    
    public User buscarPorUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            Map<String, Object> registro = jdbcTemplate.queryForMap(sql, username);
            String id = registro.get("id").toString();
            String user = (String) registro.get("username");
            String pass = (String) registro.get("password");
            return new User(id, user, pass);
        } catch (Exception e) {
            return null;
        }
    }
    
    public User buscarPorId(String id) {
        String sql = "SELECT * FROM users WHERE id = ?::uuid";
        try {
            Map<String, Object> registro = jdbcTemplate.queryForMap(sql, id);
            String userId = registro.get("id").toString();
            String username = (String) registro.get("username");
            String password = (String) registro.get("password");
            return new User(userId, username, password);
        } catch (Exception e) {
            return null;
        }
    }
}
