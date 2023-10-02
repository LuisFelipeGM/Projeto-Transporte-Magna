package br.com.magna.trainees.transporte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.magna.trainees.transporte.models.ViagemModel;

@Repository
public interface ViagemRepository extends JpaRepository<ViagemModel, Long> {

}
