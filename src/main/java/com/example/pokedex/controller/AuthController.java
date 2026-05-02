package com.example.pokedex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.pokedex.model.User;
import com.example.pokedex.model.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private ApplicationContext context;
    
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String fazerLogin(@ModelAttribute User user, HttpSession session, Model model) {
        UserService userService = context.getBean(UserService.class);
        User usuarioAutenticado = userService.autenticar(user.getUsername(), user.getPassword());
        
        if (usuarioAutenticado != null) {
            // Salva na sessão
            session.setAttribute("usuarioId", usuarioAutenticado.getId());
            session.setAttribute("username", usuarioAutenticado.getUsername());
            return "redirect:/pokemon";
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos!");
            return "login";
        }
    }
    
    @GetMapping("/registrar")
    public String mostrarRegistro(Model model) {
        model.addAttribute("user", new User());
        return "registrar";
    }
    
    @PostMapping("/registrar")
    public String fazerRegistro(@ModelAttribute User user, Model model) {
        UserService userService = context.getBean(UserService.class);
        
        try {
            userService.registrarUsuario(user);
            model.addAttribute("sucesso", "Usuário criado com sucesso! Faça login agora.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao registrar usuário. Usuário já existe?");
            return "registrar";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
