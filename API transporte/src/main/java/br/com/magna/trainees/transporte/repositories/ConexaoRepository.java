package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.ConexaoModel;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConexaoRepository extends JpaRepository<ConexaoModel, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_CONEXAO; ALTER SEQUENCE transporte.tb_conexao_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();

}
