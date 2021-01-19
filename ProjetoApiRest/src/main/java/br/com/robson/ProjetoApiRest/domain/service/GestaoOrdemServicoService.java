package br.com.robson.ProjetoApiRest.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.robson.ProjetoApiRest.api.model.Comentario;
import br.com.robson.ProjetoApiRest.domain.exception.EntidadeNaoEncontradaException;
import br.com.robson.ProjetoApiRest.domain.exception.NegocioException;
import br.com.robson.ProjetoApiRest.domain.model.Cliente;
import br.com.robson.ProjetoApiRest.domain.model.OrdemServico;
import br.com.robson.ProjetoApiRest.domain.model.StatusOrdemServico;
import br.com.robson.ProjetoApiRest.domain.repository.ClienteRepository;
import br.com.robson.ProjetoApiRest.domain.repository.ComentarioRepository;
import br.com.robson.ProjetoApiRest.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico){
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		ordemServico.setCliente(cliente);
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao){
		
		OrdemServico ordemServico = buscar(ordemServicoId);
				
		
		Comentario comentario = new Comentario();
		comentario.setDescricao(descricao);
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
		
	}
	
	public void finalizar(Long ordemServicoId){
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	private OrdemServico buscar(Long ordemServicoId){
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrado"));
		
		return ordemServico;
	}
	

}
