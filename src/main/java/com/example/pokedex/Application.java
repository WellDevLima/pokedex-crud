package com.example.pokedex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
public CommandLineRunner criarTabelas(JdbcTemplate jdbc) {
    return args -> {
        try {
            // Habilitar extensão UUID
            jdbc.execute("CREATE EXTENSION IF NOT EXISTS pgcrypto");
            
            jdbc.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id UUID PRIMARY KEY DEFAULT gen_random_uuid()," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            jdbc.execute(
                "CREATE TABLE IF NOT EXISTS pokemon (" +
                "id UUID PRIMARY KEY DEFAULT gen_random_uuid()," +
                "nome VARCHAR(100) NOT NULL," +
                "tipo1 VARCHAR(50)," +
                "descricao TEXT," +
                "nome_arquivo_foto VARCHAR(255) NOT NULL," +
                "usuario_id UUID NOT NULL REFERENCES users(id)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            System.out.println("✅ Tabelas criadas!");
        } catch (Exception e) {
            System.out.println("Erro tabelas: " + e.getMessage());
        }
    };
}
}
