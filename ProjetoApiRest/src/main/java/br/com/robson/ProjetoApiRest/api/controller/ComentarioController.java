package br.com.robson.ProjetoApiRest.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.robson.ProjetoApiRest.api.model.Comentario;
import br.com.robson.ProjetoApiRest.domain.exception.EntidadeNaoEncontradaException;
import br.com.robson.ProjetoApiRest.domain.model.OrdemServico;
import br.com.robson.ProjetoApiRest.domain.repository.OrdemServicoRepository;
import br.com.robson.ProjetoApiRest.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;	
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Comentario adicionar(@PathVariable("ordemServicoId") Long ordemServicoId, @Valid @RequestBody Comentario comentario){
		
		Comentario comentario2 = gestaoOrdemServico.adicionarComentario(ordemServicoId, comentario.getDescricao());
		
		return comentario2;
	}
	
	@GetMapping
	public List<Comentario> listar(@PathVariable Long ordemServicoId){
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem Serviço não encontrada"));
		
		return ordemServico.getComentarios();
	}
}
