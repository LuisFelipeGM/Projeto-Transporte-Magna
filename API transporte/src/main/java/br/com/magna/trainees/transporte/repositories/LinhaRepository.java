package br.com.magna.trainees.transporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.trainees.transporte.models.LinhaModel;
import jakarta.transaction.Transactional;

@Repository
public interface LinhaRepository extends JpaRepository<LinhaModel, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_LINHA; ALTER SEQUENCE transporte.tb_linha_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();

}