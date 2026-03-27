
package com.example.aulabd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Aluno {

    private String id, nome, cpf;

    //Constrtuor para a pagina do formulario
    public Aluno(){
        
    }

    //Construtor bom para Select
    public Aluno(String cpf, String id, String nome) {
        this.cpf = cpf;
        this.id = id;
        this.nome = nome;
    }

    //Construtor bom para insert
    public Aluno(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static Aluno converter(Map<String,Object> registro){
        String nome = (String) registro.get("nome");
        UUID id = (UUID) registro.get("id");
        String cpf = (String) registro.get("cpf");
        return new Aluno(cpf,id.toString(),nome);
    }

    public static ArrayList<Aluno> converterTodos(List<Map<String,Object>> registros){
        ArrayList<Aluno> aux = new ArrayList<>();
        for(Map<String,Object> registro : registros){
            aux.add(converter(registro));
        }
        return aux;
    }

}
