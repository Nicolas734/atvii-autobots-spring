package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
public class ExcluirEndereco {

		@Autowired
		private ClienteRepositorio repositorio;
		
		@Autowired
		private EnderecoRepositorio enderecoRepo;
		
		@DeleteMapping("/excluir-endereco/{id}")
		public ResponseEntity<?> excluirClienteEndereco(@PathVariable Long id) {
			List<Cliente> clientes = repositorio.findAll();
			Endereco endereco = enderecoRepo.findById(id).orElse(null); 
			if(endereco == null) {
				return new ResponseEntity<>("Endereco não encontrado", HttpStatus.NOT_FOUND);
			}else {
				for (Cliente cliente:clientes) {
					if(cliente.getEndereco() != null) {
						if(cliente.getEndereco().getId() == id) {
							cliente.setEndereco(null);
							repositorio.save(cliente);
							break;
						}
					}
				}
				return new ResponseEntity<>("Excluido com sucesso...", HttpStatus.ACCEPTED);
			}
		}
}
