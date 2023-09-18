package br.com.magna.trainees.transporte.interfaces;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

public interface IEntityService<T> {

	@Transactional
	List<T> getAll();
	
	@Transactional
	@NotNull
	T save(T object);
	
	@Transactional
	void deleteById(Long id);
	
	@Transactional
	Optional<T> findById(Long id);
	
}
