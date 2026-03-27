package com.example.aulabd.model;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class AlunoDAO {

    @Autowired
	DataSource dataSource;
	
	JdbcTemplate jdbc;
	
	@PostConstruct
	private void initialize() {
		jdbc = new JdbcTemplate(dataSource);
	}
	
	public void inserirAluno(Aluno aluno) {
		String sql = "INSERT INTO aluno(nome,cpf)" +
	                 " VALUES (?,?)";
		Object[] obj = new Object[2];
		//primeiro ?
		obj[0] = aluno.getNome();
		//segundo ?
		obj[1] = aluno.getCpf();
		jdbc.update(sql, obj);
	}

	public Aluno mostrarAluno(String uuid){
		String sql = "SELECT * FROM aluno where id=?::uuid";
		return Aluno.converter(jdbc.queryForMap(sql,uuid));
	}


	public ArrayList<Aluno> listarAlunos(){
		String sql = "SELECT * FROM aluno";
		return Aluno.converterTodos(jdbc.queryForList(sql));
	}
}
