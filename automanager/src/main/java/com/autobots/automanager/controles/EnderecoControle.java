package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	
	@Autowired
	private EnderecoRepositorio repositorio;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> buscarEnderecos(){
		List<Endereco> enderecos = repositorio.findAll();
		adicionadorLink.adicionarLink(enderecos);
		for(Endereco endereco: enderecos) {
			adicionadorLink.adicionarLinkUpdate(endereco);
			adicionadorLink.adicionarLinkDelete(endereco);
		}
		return new ResponseEntity<List<Endereco>>(enderecos,HttpStatus.FOUND);
	}
	
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> buscarEnderecoPorId(@PathVariable Long id) {
		Endereco endereco = repositorio.findById(id).orElse(null);
		HttpStatus status = null;
		if(endereco == null) {
			status = HttpStatus.NOT_FOUND;
		}else {
			status = HttpStatus.FOUND;
			adicionadorLink.adicionarLink(endereco);
			adicionadorLink.adicionarLinkUpdate(endereco);
			adicionadorLink.adicionarLinkDelete(endereco);
		}
		return new ResponseEntity<Endereco>(endereco,status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Endereco> editarEnderecoPorId(@RequestBody Endereco atualizacao) {
		Endereco enderecoSelecionado = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(enderecoSelecionado, atualizacao);
		repositorio.save(enderecoSelecionado);
		return new ResponseEntity<Endereco>(enderecoSelecionado, HttpStatus.ACCEPTED);
	}
}
