package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.ConexaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConexaoRepository extends JpaRepository<ConexaoModel, Long> {
}
