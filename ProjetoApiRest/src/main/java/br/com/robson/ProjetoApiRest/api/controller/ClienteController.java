package br.com.robson.ProjetoApiRest.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.robson.ProjetoApiRest.domain.model.Cliente;
import br.com.robson.ProjetoApiRest.domain.repository.ClienteRepository;
import br.com.robson.ProjetoApiRest.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroclienteService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente){
		
		return cadastroclienteService.salvar(cliente);
	}

	
	@GetMapping
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clinteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable("clinteId") Long clienteId){
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()){
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{clinteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable("clinteId") Long clienteId, @RequestBody Cliente cliente){
		
		if(!clienteRepository.existsById(clienteId)){
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		cliente = cadastroclienteService.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clinteId}")
	public ResponseEntity<Void> remover(@PathVariable("clinteId") Long clienteId){
		if(!clienteRepository.existsById(clienteId)){
			return ResponseEntity.notFound().build();
		}
		
		cadastroclienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}

}
