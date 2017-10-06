package com.tiago.cooHomeless.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiago.cooHomeless.model.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{
	
	Optional<Colaborador> findByNome(String nome);
	Collection<Colaborador> findByCpf(String cpf);
	Colaborador findById(Long id);
}
