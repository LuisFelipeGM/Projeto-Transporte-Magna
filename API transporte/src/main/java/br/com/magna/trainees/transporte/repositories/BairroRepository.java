package br.com.magna.trainees.transporte.repositories;

import br.com.magna.trainees.transporte.models.BairroModel;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<BairroModel, Long> {

    List<BairroModel> findBynomeContainingIgnoreCase(String nome);
    
    @Transactional
	@Modifying
	@Query(value = "DELETE FROM transporte.TB_BAIRRO; ALTER SEQUENCE transporte.tb_bairro_id_seq RESTART WITH 1;", nativeQuery = true)
	void LimparDadosERedefinirSequence();

}
