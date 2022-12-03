package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.AdicionadorLinkCliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;

	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, id);
		HttpStatus status = null;
		if(cliente == null) {
			status = HttpStatus.NOT_FOUND;
		}else {
			adicionadorLink.adicionarLink(cliente);
			adicionadorLink.adicionarLinkUpdate(cliente);
			adicionadorLink.adicionarLinkDelete(cliente);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Cliente>(cliente,status);
	}

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		adicionadorLink.adicionarLink(clientes);
		for(Cliente cliente: clientes) {
			adicionadorLink.adicionarLinkUpdate(cliente);
			adicionadorLink.adicionarLinkDelete(cliente);
		}
		return new ResponseEntity<List<Cliente>>(clientes,HttpStatus.FOUND);
	}

	@PostMapping("/cadastro")
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
		Cliente resposta = repositorio.save(cliente);
		adicionadorLink.adicionarLinkUpdate(resposta);
		adicionadorLink.adicionarLinkDelete(resposta);
		return new ResponseEntity<Cliente>(cliente,HttpStatus.CREATED);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Cliente> atualizarCliente(@RequestBody Cliente atualizacao) {
		Cliente cliente = repositorio.getById(atualizacao.getId());
		ClienteAtualizador atualizador = new ClienteAtualizador();
		atualizador.atualizar(cliente, atualizacao);
		repositorio.save(cliente);
		return new ResponseEntity<Cliente>(cliente,HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirCliente(@RequestBody Cliente exclusao) {
		Cliente cliente = repositorio.getById(exclusao.getId());
		repositorio.delete(cliente);
		return new ResponseEntity<>("Excluido com sucesso...",HttpStatus.ACCEPTED);
	}
}
