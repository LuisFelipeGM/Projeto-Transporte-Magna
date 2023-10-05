package br.com.magna.trainees.transporte.interfaces;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

public interface IEntityService<T> {

	@Transactional
	List<T> getAll();

	@Transactional
	void deleteById(Long id);

	@Transactional
	Optional<T> findById(Long id);

}
