package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.EnderecoModel;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_ENDERECO; ALTER SEQUENCE transporte.tb_endereco_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();
	
}
