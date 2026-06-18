package com.example.pokedex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonService;
import com.example.pokedex.model.PokemonService.PokemonAPIData;

import jakarta.servlet.http.HttpSession;

@Controller
public class PokemonController {
    
    @Autowired
    private ApplicationContext context;
    
    @GetMapping("/pokemon")
    public String formularioPokemon(HttpSession session, Model model) {
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }
        model.addAttribute("pokemon", new Pokemon());
        return "formpokemon";
    }
    
    @PostMapping("/pokemon")
    public String adicionarPokemon(
            @RequestParam String nome,
            HttpSession session,
            Model model) {
        
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        try {
            PokemonService pokemonService = context.getBean(PokemonService.class);
            PokemonAPIData dadosAPI = pokemonService.buscarDadosDaAPI(nome);

            if (dadosAPI == null) {
                model.addAttribute("erro", "Pokémon '" + nome + "' não encontrado! Use o nome em inglês (ex: pikachu).");
                model.addAttribute("pokemon", new Pokemon());
                return "formpokemon";
            }

            Pokemon pokemon = new Pokemon(nome, dadosAPI.tipo1, dadosAPI.tipo2, dadosAPI.descricao, dadosAPI.imagemUrl, usuarioId);
            pokemonService.inserirPokemon(pokemon);
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            model.addAttribute("pokemon", new Pokemon());
            return "formpokemon";
        }
    }
    
    @GetMapping("/pokemon/editar")
    public String formularioEditar(
            @RequestParam String id,
            HttpSession session,
            Model model) {
        
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        try {
            PokemonService pokemonService = context.getBean(PokemonService.class);
            Pokemon pokemon = pokemonService.buscarPorId(id);
            
            if (pokemon == null || !pokemon.getUsuarioId().equals(usuarioId)) {
                model.addAttribute("erro", "Pokémon não encontrado ou você não tem permissão para editar");
                return "redirect:/";
            }
            
            model.addAttribute("pokemon", pokemon);
            model.addAttribute("modo", "editar");
            return "editar-pokemon";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar pokémon: " + e.getMessage());
            return "redirect:/";
        }
    }
    
    @PostMapping("/pokemon/editar")
    public String atualizarPokemon(
            @RequestParam String id,
            @RequestParam String nome,
            HttpSession session,
            Model model) {
        
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        try {
            PokemonService pokemonService = context.getBean(PokemonService.class);
            
            // Busca o pokémon existente
            Pokemon pokemonExistente = pokemonService.buscarPorId(id);
            if (pokemonExistente == null || !pokemonExistente.getUsuarioId().equals(usuarioId)) {
                model.addAttribute("erro", "Pokémon não encontrado ou você não tem permissão");
                return "redirect:/";
            }
            
            // Se o nome mudou, busca novos dados da API
            PokemonAPIData dadosAPI;
            if (!pokemonExistente.getNome().equalsIgnoreCase(nome)) {
                dadosAPI = pokemonService.buscarDadosDaAPI(nome);
                if (dadosAPI == null) {
                    model.addAttribute("erro", "Pokémon '" + nome + "' não encontrado! Use o nome em inglês.");
                    model.addAttribute("pokemon", pokemonExistente);
                    model.addAttribute("modo", "editar");
                    return "editar-pokemon";
                }
            } else {
                // Se o nome é o mesmo, mantém os dados originais
                dadosAPI = new PokemonAPIData(pokemonExistente.getTipo1(), pokemonExistente.getTipo2(), pokemonExistente.getDescricao(), pokemonExistente.getImagemUrl());
            }
            
            // Atualiza o pokémon
            Pokemon pokemonAtualizado = new Pokemon(id, nome, dadosAPI.tipo1, dadosAPI.tipo2, dadosAPI.descricao, dadosAPI.imagemUrl, usuarioId);
            pokemonService.atualizarPokemon(pokemonAtualizado);
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao atualizar: " + e.getMessage());
            return "redirect:/";
        }
    }
    
    @PostMapping("/pokemon/deletar")
    public String deletarPokemon(
            @RequestParam String id,
            HttpSession session,
            Model model) {
        
        String usuarioId = (String) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        try {
            PokemonService pokemonService = context.getBean(PokemonService.class);
            pokemonService.deletarPokemon(id, usuarioId);
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao deletar: " + e.getMessage());
            return "redirect:/";
        }
    }
}
