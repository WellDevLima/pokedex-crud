package com.example.pokedex.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class PokemonDAO {
    
    @Autowired
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void initialize() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void inserirPokemon(Pokemon pokemon) {
        String sql = "INSERT INTO pokemon (nome, tipo1, tipo2, descricao, usuario_id) " +
                     "VALUES (?, ?, ?, ?, ?::uuid)";
        jdbcTemplate.update(
            sql,
            pokemon.getNome(),
            pokemon.getTipo1(),
            pokemon.getTipo2(),
            pokemon.getDescricao(),
            pokemon.getUsuarioId()
        );
    }
    
    public void atualizarPokemon(Pokemon pokemon) {
        String sql = "UPDATE pokemon SET nome = ?, tipo1 = ?, tipo2 = ?, descricao = ? " +
                     "WHERE id = ?::uuid AND usuario_id = ?::uuid";
        int linhasAfetadas = jdbcTemplate.update(
            sql,
            pokemon.getNome(),
            pokemon.getTipo1(),
            pokemon.getTipo2(),
            pokemon.getDescricao(),
            pokemon.getId(),
            pokemon.getUsuarioId()
        );
        
        if (linhasAfetadas == 0) {
            throw new RuntimeException("Pokémon não encontrado ou você não tem permissão para editar");
        }
    }
    
    public void deletarPokemon(String id, String usuarioId) {
        String sql = "DELETE FROM pokemon WHERE id = ?::uuid AND usuario_id = ?::uuid";
        int linhasAfetadas = jdbcTemplate.update(sql, id, usuarioId);
        
        if (linhasAfetadas == 0) {
            throw new RuntimeException("Pokémon não encontrado ou você não tem permissão para deletar");
        }
    }
    
    public Pokemon buscarPorId(String id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?::uuid";
        try {
            Map<String, Object> registro = jdbcTemplate.queryForMap(sql, id);
            return converterParaPokemon(registro);
        } catch (Exception e) {
            return null;
        }
    }
    
    public ArrayList<Pokemon> listarTodos() {
        String sql = "SELECT * FROM pokemon ORDER BY created_at DESC";
        List<Map<String, Object>> registros = jdbcTemplate.queryForList(sql);
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        for (Map<String, Object> registro : registros) {
            pokemons.add(converterParaPokemon(registro));
        }
        return pokemons;
    }
    
    public ArrayList<Pokemon> buscarPorUsuario(String usuarioId) {
        String sql = "SELECT * FROM pokemon WHERE usuario_id = ?::uuid ORDER BY created_at DESC";
        try {
            List<Map<String, Object>> registros = jdbcTemplate.queryForList(sql, usuarioId);
            ArrayList<Pokemon> pokemons = new ArrayList<>();
            for (Map<String, Object> registro : registros) {
                pokemons.add(converterParaPokemon(registro));
            }
            return pokemons;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    private Pokemon converterParaPokemon(Map<String, Object> registro) {
        String id = registro.get("id").toString();
        String nome = (String) registro.get("nome");
        String tipo1 = (String) registro.get("tipo1");
        String tipo2 = (String) registro.get("tipo2");
        String descricao = (String) registro.get("descricao");
        String usuarioId = registro.get("usuario_id").toString();
        
        Pokemon pokemon = new Pokemon(id, nome, tipo1, tipo2, descricao, usuarioId);
        return pokemon;
    }
}
