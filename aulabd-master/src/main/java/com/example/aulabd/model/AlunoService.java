package com.example.aulabd.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    @Autowired
    AlunoDAO alunoDAO;

    public void inserirAluno(Aluno aluno){
        alunoDAO.inserirAluno(aluno);
    }

    public Aluno mostrarAluno(String uuid){
        return alunoDAO.mostrarAluno(uuid);
    }

    public ArrayList<Aluno> listarAlunos(){
        return alunoDAO.listarAlunos();
    }

}
