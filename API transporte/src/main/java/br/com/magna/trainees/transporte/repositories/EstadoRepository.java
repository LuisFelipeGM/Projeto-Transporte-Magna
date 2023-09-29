package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.EstadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoModel, Long> {

    EstadoModel findBynomeContainingIgnoreCase(String nome);

    EstadoModel findBysiglaContainingIgnoreCase(String sigla);

}
