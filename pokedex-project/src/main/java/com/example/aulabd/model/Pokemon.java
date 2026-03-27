package com.example.aulabd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Pokemon {

    private String id;
    private String nome;
    private String tipo1;
    private String tipo2;
    private String descricao;
    private String nomeArquivoFoto;

    public Pokemon() {
    }

    public Pokemon(String nome, String tipo1, String tipo2, String descricao, String nomeArquivoFoto) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descricao = descricao;
        this.nomeArquivoFoto = nomeArquivoFoto;
    }

    public Pokemon(String id, String nome, String tipo1, String tipo2, String descricao, String nomeArquivoFoto) {
        this.id = id;
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.descricao = descricao;
        this.nomeArquivoFoto = nomeArquivoFoto;
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

    public String getNomeArquivoFoto() {
        return nomeArquivoFoto;
    }

    public void setNomeArquivoFoto(String nomeArquivoFoto) {
        this.nomeArquivoFoto = nomeArquivoFoto;
    }

    public static Pokemon converter(Map<String, Object> registro) {
        if (registro == null) {
            return null;
        }
        String id = registro.get("id") != null ? registro.get("id").toString() : null;
        String nome = registro.get("nome") != null ? registro.get("nome").toString() : null;
        String tipo1 = registro.get("tipo1") != null ? registro.get("tipo1").toString() : null;
        String tipo2 = registro.get("tipo2") != null ? registro.get("tipo2").toString() : null;
        String descricao = registro.get("descricao") != null ? registro.get("descricao").toString() : null;
        String nomeArquivoFoto = registro.get("nomeArquivoFoto") != null ? registro.get("nomeArquivoFoto").toString() : null;
        return new Pokemon(id, nome, tipo1, tipo2, descricao, nomeArquivoFoto);
    }

    public static List<Pokemon> converterTodos(List<Map<String, Object>> registros) {
        List<Pokemon> pokemons = new ArrayList<>();
        if (registros == null) {
            return pokemons;
        }
        for (Map<String, Object> registro : registros) {
            Pokemon pokemon = converter(registro);
            if (pokemon != null) {
                pokemons.add(pokemon);
            }
        }
        return pokemons;
    }
}