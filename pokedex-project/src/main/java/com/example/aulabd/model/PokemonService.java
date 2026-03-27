package com.example.aulabd.model;
 
import java.util.ArrayList;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class PokemonService {
 
    @Autowired
    private PokemonDAO pokemonDAO;
 
    public void inserirPokemon(Pokemon pokemon) {
        pokemonDAO.inserirPokemon(pokemon);
    }
 
    public Pokemon mostrarPokemon(String uuid) {
        return pokemonDAO.mostrarPokemon(uuid);
    }
 
    public ArrayList<Pokemon> listarPokemons() {
        return (ArrayList<Pokemon>) pokemonDAO.listarPokemons();
    }
 
    public ArrayList<Pokemon> buscarPorNome(String nome) {
        return pokemonDAO.buscarPorNome(nome);
    }
}