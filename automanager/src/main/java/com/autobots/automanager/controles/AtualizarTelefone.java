package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
public class AtualizarTelefone {
	
	@Autowired
	private ClienteRepositorio repositorio;
	
	@PutMapping("/atualizar-telefone")
	public void atualizarClienteTelefone(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		selecionado.getTelefones().addAll(cliente.getTelefones());
		repositorio.save(selecionado);
	}
}