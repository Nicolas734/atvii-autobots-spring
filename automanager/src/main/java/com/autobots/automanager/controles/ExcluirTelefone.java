package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class ExcluirTelefone {
	@Autowired
	private ClienteRepositorio repositorio;
	
	@Autowired
	private TelefoneRepositorio tellRepo;
	
	@DeleteMapping("/excluir-telefone")
	public void excluirTelefone(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		for (Telefone tell:cliente.getTelefones()) {
			 Telefone telefoneSelecionado = tellRepo.getById(tell.getId());
			 selecionado.getTelefones().remove(telefoneSelecionado);
		}
		repositorio.save(selecionado);
	}
	
	@DeleteMapping("/excluir-telefones")
	public void excluirTelefones(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		selecionado.getTelefones().clear();
		repositorio.save(selecionado);
	}
}