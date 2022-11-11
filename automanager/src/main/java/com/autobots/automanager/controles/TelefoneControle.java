package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;


@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	
	@GetMapping("/telefones")
	public List<Telefone> buscarTelefones(){
		return repositorio.findAll();
	}
	
	@GetMapping("/telefone/{id}")
	public Telefone buscarTelefone(@PathVariable Long id) {
		Telefone selecionado = repositorio.findById(id).get();
		return selecionado;
	}
	
	@PutMapping("/atualizar")
	public void editarTelefonePorId(@RequestBody Telefone atualizacao) {
		Telefone telefoneSelecionado = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefoneSelecionado, atualizacao);
		repositorio.save(telefoneSelecionado);
	}
}
