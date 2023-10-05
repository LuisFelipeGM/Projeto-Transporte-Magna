package br.com.magna.trainees.transporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.trainees.transporte.models.ViagemModel;
import jakarta.transaction.Transactional;

@Repository
public interface ViagemRepository extends JpaRepository<ViagemModel, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_VIAGEM; ALTER SEQUENCE transporte.tb_viagem_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
