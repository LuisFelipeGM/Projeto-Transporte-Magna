package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.BairroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<BairroModel, Long> {

    BairroModel findBynomeContainingIgnoreCase(String nome);

}
