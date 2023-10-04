package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.EstacaoModel;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacaoRepository extends JpaRepository<EstacaoModel, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_ESTACAO; ALTER SEQUENCE transporte.tb_estacao_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
