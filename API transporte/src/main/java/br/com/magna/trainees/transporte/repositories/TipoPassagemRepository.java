package br.com.magna.trainees.transporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.trainees.transporte.models.TipoPassagemModel;
import jakarta.transaction.Transactional;

@Repository
public interface TipoPassagemRepository extends JpaRepository<TipoPassagemModel, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_TIPO_PASSAGEM; ALTER SEQUENCE transporte.tb_tipo_passagem_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
