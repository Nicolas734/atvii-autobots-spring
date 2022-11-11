package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class ExcluirEndereco {

		@Autowired
		private ClienteRepositorio repositorio;
		
		@Autowired
		private EnderecoRepositorio enderecoRepo;
		
		@DeleteMapping("/excluir-endereco")
		public void excluirClienteEndereco(@RequestBody Cliente cliente) {
			Cliente selecionado = repositorio.getById(cliente.getId());
			enderecoRepo.delete(selecionado.getEndereco());
			selecionado.setEndereco(null);
			repositorio.save(selecionado);
		}
}
