package com.example.pokedex.model;

import java.util.UUID;

public class Pokemon {
    
    private String id;
    private String nome;
    private String tipo1;
    private String tipo2;
    private String descricao;
    private String usuarioId;
    
    public Pokemon() {
    }
    
    public Pokemon(String nome, String tipo1, String tipo2, String descricao, String usuarioId) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descricao = descricao;
        this.usuarioId = usuarioId;
    }
    
    public Pokemon(String id, String nome, String tipo1, String tipo2, String descricao, String usuarioId) {
        this.id = id;
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descricao = descricao;
        this.usuarioId = usuarioId;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTipo1() {
        return tipo1;
    }
    
    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }
    
    public String getTipo2() {
        return tipo2;
    }
    
    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
