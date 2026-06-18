package com.example.pokedex.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PokemonService {
    
    @Autowired
    private PokemonDAO pokemonDAO;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public void inserirPokemon(Pokemon pokemon) {
        pokemonDAO.inserirPokemon(pokemon);
    }
    
    public void atualizarPokemon(Pokemon pokemon) {
        pokemonDAO.atualizarPokemon(pokemon);
    }
    
    public void deletarPokemon(String id, String usuarioId) {
        pokemonDAO.deletarPokemon(id, usuarioId);
    }
    
    public Pokemon buscarPorId(String id) {
        return pokemonDAO.buscarPorId(id);
    }
    
    public ArrayList<Pokemon> listarTodos() {
        return pokemonDAO.listarTodos();
    }
    
    public ArrayList<Pokemon> buscarPorUsuario(String usuarioId) {
        return pokemonDAO.buscarPorUsuario(usuarioId);
    }
    
    /**
     * Consulta a PokéAPI e retorna tipos e descrição
     * @param nomePokemon - nome do pokémon em minúsculas
     * @return PokemonAPIData com tipo1, tipo2 e descrição, ou null se não encontrar
     */
    public PokemonAPIData buscarDadosDaAPI(String nomePokemon) {
        try {
            String url = "https://pokeapi.co/api/v2/pokemon/" + nomePokemon.toLowerCase();
            String response = restTemplate.getForObject(url, String.class);
            
            if (response == null) {
                return null;
            }
            
            JsonNode root = objectMapper.readTree(response);
            
            // Extrair tipo(s)
            String tipo1 = null;
            String tipo2 = null;
            
            if (root.has("types")) {
                JsonNode types = root.get("types");
                for (int i = 0; i < types.size(); i++) {
                    JsonNode type = types.get(i);
                    int slot = type.get("slot").asInt();
                    String typeName = type.get("type").get("name").asText();
                    
                    if (slot == 1) {
                        tipo1 = capitalize(typeName);
                    } else if (slot == 2) {
                        tipo2 = capitalize(typeName);
                    }
                }
            }
            
            // Extrair descrição da espécie (é outro endpoint)
            String descricao = "Pokémon desconhecido";
            try {
                if (root.has("species")) {
                    String speciesUrl = root.get("species").get("url").asText();
                    String speciesResponse = restTemplate.getForObject(speciesUrl, String.class);
                    JsonNode speciesRoot = objectMapper.readTree(speciesResponse);
                    
                    // Tentar pegar descrição em português ou inglês
                    if (speciesRoot.has("flavor_text_entries")) {
                        JsonNode flavorTexts = speciesRoot.get("flavor_text_entries");
                        for (int i = 0; i < flavorTexts.size(); i++) {
                            JsonNode entry = flavorTexts.get(i);
                            String language = entry.get("language").get("name").asText();
                            if (language.equals("pt") || language.equals("en")) {
                                descricao = entry.get("flavor_text").asText()
                                    .replaceAll("\\n", " ")
                                    .replaceAll("\\r", "");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            
            return new PokemonAPIData(tipo1, tipo2, descricao);
            
        } catch (RestClientException | com.fasterxml.jackson.core.JsonProcessingException e) {
            return null;
        }
    }
    
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    /**
     * DTO para dados da API
     */
    public static class PokemonAPIData {
        public String tipo1;
        public String tipo2;
        public String descricao;
        public String imagemUrl; 
        
        public PokemonAPIData(String tipo1, String tipo2, String descricao) {
            this.tipo1 = tipo1;
            this.tipo2 = tipo2;
            this.descricao = descricao;
            this.imagemUrl = imagemUrl;
        }
    }
}
