package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	
	@Autowired
	private EnderecoRepositorio repositorio;
	
	@GetMapping("/enderecos")
	public List<Endereco> buscarEnderecos(){
		return repositorio.findAll();
	}
	
	@GetMapping("/endereco/{id}")
	public Endereco buscarEnderecoPorId(@PathVariable Long id) {
		return repositorio.findById(id).get();
	}
	
	@PutMapping("/atualizar")
	public void editarEnderecoPorId(@RequestBody Endereco atualizacao) {
		Endereco enderecoSelecionado = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(enderecoSelecionado, atualizacao);
		repositorio.save(enderecoSelecionado);
	}
}
