package br.com.robson.ProjetoApiRest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.robson.ProjetoApiRest.api.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> { }
