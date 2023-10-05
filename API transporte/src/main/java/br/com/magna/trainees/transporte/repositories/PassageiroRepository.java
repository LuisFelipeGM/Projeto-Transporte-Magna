package br.com.magna.trainees.transporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.magna.trainees.transporte.models.PassageiroModel;
import jakarta.transaction.Transactional;

@Repository
public interface PassageiroRepository extends JpaRepository<PassageiroModel, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_PASSAGEIRO; ALTER SEQUENCE transporte.tb_passageiro_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
