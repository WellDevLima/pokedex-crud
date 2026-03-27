
package com.example.aulabd.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.aulabd.model.Aluno;
import com.example.aulabd.model.AlunoService;

@Controller
public class PaginaController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/aluno")
	public String formAluno(Model model) {
		model.addAttribute("aluno",new Aluno());
		return "formaluno";
	}
	
	@PostMapping("/aluno")
	public String postAluno(@ModelAttribute Aluno aluno,
			                  Model model) {
        //AlunoService eh feito via autowired
		AlunoService cs = context.getBean(AlunoService.class);
		cs.inserirAluno(aluno);
		return "sucesso";
	}

	@GetMapping("/perfil/{uuid}")
	public String verPerfil(@PathVariable String uuid, Model model){
		AlunoService cs = context.getBean(AlunoService.class);
		Aluno aluno = cs.mostrarAluno(uuid);
		model.addAttribute("idAluno",aluno.getId());
		model.addAttribute("cpfAluno",aluno.getCpf());
		model.addAttribute("nomeAluno",aluno.getNome());
		return "paginaaluno";
	}

	@GetMapping("/listar")
	public String listarAlunos(Model model){
		AlunoService cs = context.getBean(AlunoService.class);
		ArrayList<Aluno> alunos = (ArrayList<Aluno>) cs.listarAlunos();
		model.addAttribute("alunos",alunos);
		return "listaraluno";
	}

}
