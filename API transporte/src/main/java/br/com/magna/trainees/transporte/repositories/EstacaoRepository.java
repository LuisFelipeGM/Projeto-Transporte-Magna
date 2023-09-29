package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.EstacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacaoRepository extends JpaRepository<EstacaoModel, Long> {
}
