package br.com.robson.ProjetoApiRest.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.robson.ProjetoApiRest.api.model.OrdemServicoModel;
import br.com.robson.ProjetoApiRest.domain.model.OrdemServico;
import br.com.robson.ProjetoApiRest.domain.repository.OrdemServicoRepository;
import br.com.robson.ProjetoApiRest.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServico ordemServico){
		
		return toModel(gestaoOrdemServico.criar(ordemServico));		
	}
	
	@GetMapping
	public List<OrdemServicoModel> listar(){
		
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable("ordemServicoId") Long ordemServicoId){

		gestaoOrdemServico.finalizar(ordemServicoId);

	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable("ordemServicoId") Long ordemServicoId){
		
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()){
			OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
			return ResponseEntity.ok(ordemServicoModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico){
		return  modelmapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico){
		return  ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	
	
}
