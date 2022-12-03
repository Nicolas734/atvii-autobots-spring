package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@DeleteMapping("/excluir-telefone/{id}")
	public ResponseEntity<?> excluirTelefone(@PathVariable Long id) {
		List<Cliente> clientes = repositorio.findAll();
		Telefone telefone = tellRepo.findById(id).orElse(null); 
		if(telefone == null) {
			return new ResponseEntity<>("Telefone não encontrado", HttpStatus.NOT_FOUND);
		}else {
			for (Cliente cliente:clientes) {
				for(Telefone telefoneCliente: cliente.getTelefones()) {
					if(telefoneCliente.getId() == id) {
						cliente.getTelefones().remove(telefoneCliente);
						repositorio.save(cliente);
						break;
					}
				}
			}
			return new ResponseEntity<>("Excluido com sucesso...", HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping("/excluir-telefones")
	public void excluirTelefones(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		selecionado.getTelefones().clear();
		repositorio.save(selecionado);
	}
}