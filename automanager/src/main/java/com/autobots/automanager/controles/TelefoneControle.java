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

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;


@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	
	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> buscarTelefones(){
		List<Telefone> telefones = repositorio.findAll();
		adicionadorLink.adicionarLink(telefones);
		for(Telefone telefone: telefones) {
			adicionadorLink.adicionarLinkUpdate(telefone);
			adicionadorLink.adicionarLinkDelete(telefone);
		}
		return new ResponseEntity<List<Telefone>>(telefones,HttpStatus.FOUND);
	}
	
	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> buscarTelefone(@PathVariable Long id) {
		Telefone selecionado = repositorio.findById(id).orElse(null);
		HttpStatus status = null;
		if(selecionado == null) {
			status = HttpStatus.NOT_FOUND;
		}else {
			adicionadorLink.adicionarLink(selecionado);
			adicionadorLink.adicionarLinkUpdate(selecionado);
			adicionadorLink.adicionarLinkDelete(selecionado);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Telefone>(selecionado,status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Telefone> editarTelefonePorId(@RequestBody Telefone atualizacao) {
		Telefone telefoneSelecionado = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefoneSelecionado, atualizacao);
		repositorio.save(telefoneSelecionado);
		return new ResponseEntity<Telefone>(telefoneSelecionado,HttpStatus.ACCEPTED);
	}
}
