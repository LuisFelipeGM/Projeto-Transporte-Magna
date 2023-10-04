package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.CidadeModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeModel, Long> {

    CidadeModel findBynomeContainingIgnoreCase(String nome);
    

}
