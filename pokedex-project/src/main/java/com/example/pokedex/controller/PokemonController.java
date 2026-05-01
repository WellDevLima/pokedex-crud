package com.example.pokedex.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonService;
import com.example.pokedex.model.PokemonService.PokemonAPIData;

import jakarta.servlet.http.HttpSession;

@Controller
public class PokemonController {
    
    @Autowired
    private ApplicationContext context;
    
    private final String UPLOAD_DIR = "uploads/";
    
    @GetMapping("/pokemon")
    public String formularioPokemon(HttpSession session, Model model) {
        // Verificar se está logado
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
            @RequestParam("arquivo") MultipartFile arquivo,
            HttpSession session,
            Model model) throws IOException {
        
        // Verificar se está logado
        String usuarioId = (String) session.getAttribute("usuarioId");
        
        if (usuarioId == null) {
            return "redirect:/login";
        }
        
        // Validar arquivo
        if (arquivo.isEmpty()) {
            model.addAttribute("erro", "Selecione uma imagem!");
            return "formpokemon";
        }
        
        // Salvar arquivo
        String nomeArquivo = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
        String caminhoCompleto = UPLOAD_DIR + nomeArquivo;
        arquivo.transferTo(new File(caminhoCompleto));
        
        // Consultar API para tipo e descrição
        PokemonService pokemonService = context.getBean(PokemonService.class);
        PokemonAPIData dadosAPI = pokemonService.buscarDadosDaAPI(nome);
        
        if (dadosAPI == null) {
            model.addAttribute("erro", "Pokémon não encontrado na API! Verifique o nome.");
            return "formpokemon";
        }
        
        // Criar e salvar pokémon
        Pokemon pokemon = new Pokemon(
            nome,
            dadosAPI.tipo1,
            dadosAPI.descricao,
            nomeArquivo,
            usuarioId
        );
        
        pokemonService.inserirPokemon(pokemon);
        
        return "redirect:/";
    }
}
