package com.example.pokedex.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @Autowired
    private ApplicationContext context;
    
    @GetMapping("/")
public String home(Model model, HttpSession session) {
    try {
        PokemonService pokemonService = context.getBean(PokemonService.class);
        ArrayList<Pokemon> pokemons = pokemonService.listarTodos();
        model.addAttribute("pokemons", pokemons);
    } catch (Exception e) {
        model.addAttribute("pokemons", new ArrayList<>());
    }

    String usuarioId = (String) session.getAttribute("usuarioId");
    String username = (String) session.getAttribute("username");

    if (usuarioId != null) {
        model.addAttribute("usuarioLogado", true);
        model.addAttribute("username", username);
    } else {
        model.addAttribute("usuarioLogado", false);
    }

    return "index";
    }
}
