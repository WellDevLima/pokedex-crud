package com.example.aulabd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class PokemonService {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void inserirPokemon(Pokemon pokemon) {
        String sql = "INSERT INTO pokemon (nome, tipo1, tipo2, descricao, nome_arquivo_foto) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
            sql,
            pokemon.getNome(),
            pokemon.getTipo1(),
            pokemon.getTipo2(),
            pokemon.getDescricao(),
            pokemon.getNomeArquivoFoto()
        );
    }

    public Pokemon mostrarPokemon(String uuid) {
        String sql = "SELECT * FROM pokemon WHERE id = ?::uuid";
        Map<String, Object> registro = jdbcTemplate.queryForMap(sql, uuid);
        return Pokemon.converter(registro);
    }

    public List<Pokemon> listarPokemons() {
        String sql = "SELECT * FROM pokemon";
        List<Map<String, Object>> registros = jdbcTemplate.queryForList(sql);
        return Pokemon.converterTodos(registros);
    }

    public ArrayList<Pokemon> buscarPorNome(String nome) {
        String sql = "SELECT * FROM pokemon WHERE nome ILIKE '%' || ? || '%'";
        List<Map<String, Object>> registros = jdbcTemplate.queryForList(sql, nome);
        List<Pokemon> lista = Pokemon.converterTodos(registros);
        return new ArrayList<>(lista);
    }
}