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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
public class ExcluirDocumento {
	
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private DocumentoRepositorio docRepo;
	
	@DeleteMapping("/excluir-documento/{id}")
	public ResponseEntity<?> excluirDocumento(@PathVariable Long id) {
		List<Cliente> clientes = repositorio.findAll();
		Documento documento = docRepo.findById(id).orElse(null); 
		if(documento == null) {
			return new ResponseEntity<>("Documento não encontrado", HttpStatus.NOT_FOUND);
		}else {
			for (Cliente cliente:clientes) {
				for(Documento documentoCliente: cliente.getDocumentos()) {
					if(documentoCliente.getId() == id) {
						cliente.getDocumentos().remove(documentoCliente);
						repositorio.save(cliente);
						break;
					}
				}
			}
			return new ResponseEntity<>("Excluido com sucesso...", HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping("/excluir-documentos")
	public ResponseEntity<?> excluirDocumentos(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		selecionado.getDocumentos().clear();
		repositorio.save(selecionado);
		return new ResponseEntity<>("Excluido com sucesso...", HttpStatus.ACCEPTED);
	}
}
