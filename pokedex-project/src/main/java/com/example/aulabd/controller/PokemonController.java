package com.example.aulabd.controller;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
 
import com.example.aulabd.model.Pokemon;
import com.example.aulabd.model.PokemonService;
 
@Controller
public class PokemonController {
 
    @Autowired
    private ApplicationContext context;
 
    private final String UPLOAD_DIR = "uploads/";
 
    @GetMapping("/")
    public String index(Model model) {
        PokemonService ps = context.getBean(PokemonService.class);
        ArrayList<Pokemon> pokemons = ps.listarPokemons();
        model.addAttribute("pokemons", pokemons);
        return "index";
    }
 
    @GetMapping("/pokemon")
    public String formPokemon(Model model) {
        model.addAttribute("pokemon", new Pokemon());
        return "formpokemon";
    }
 
    @PostMapping("/pokemon")
    public String postPokemon(@ModelAttribute Pokemon pokemon,
                              @RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        
        if (!arquivo.isEmpty()) {
            String nomeArquivo = arquivo.getOriginalFilename();
            String caminhoCompleto = UPLOAD_DIR + nomeArquivo;
            arquivo.transferTo(new File(caminhoCompleto));
            pokemon.setNomeArquivoFoto(nomeArquivo);
        }
        
        PokemonService ps = context.getBean(PokemonService.class);
        ps.inserirPokemon(pokemon);
        return "redirect:/";
    }
 
    @GetMapping("/pokemon/{uuid}")
    public String verPokemon(@PathVariable String uuid, Model model) {
        PokemonService ps = context.getBean(PokemonService.class);
        Pokemon pokemon = ps.mostrarPokemon(uuid);
        model.addAttribute("pokemon", pokemon);
        return "perfilpokemon";
    }
 
    @GetMapping("/buscar")
    public String buscarPokemon(@RequestParam String nome, Model model) {
        PokemonService ps = context.getBean(PokemonService.class);
        ArrayList<Pokemon> pokemons = ps.buscarPorNome(nome);
        model.addAttribute("pokemons", pokemons);
        model.addAttribute("busca", nome);
        return "index";
    }
}