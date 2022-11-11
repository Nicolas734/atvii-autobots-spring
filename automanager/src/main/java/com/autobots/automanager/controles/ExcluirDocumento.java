package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@DeleteMapping("/excluir-documento")
	public void excluirDocumento(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		for (Documento doc:cliente.getDocumentos()) {
			 Documento documentoSelecionado = docRepo.getById(doc.getId());
			 selecionado.getDocumentos().remove(documentoSelecionado);
		}
		repositorio.save(selecionado);
	}
	
	@DeleteMapping("/excluir-documentos")
	public void excluirDocumentos(@RequestBody Cliente cliente) {
		Cliente selecionado = repositorio.getById(cliente.getId());
		selecionado.getDocumentos().clear();
		repositorio.save(selecionado);
	}
}
