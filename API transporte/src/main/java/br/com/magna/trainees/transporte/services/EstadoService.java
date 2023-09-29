package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.models.EstadoModel;
import br.com.magna.trainees.transporte.repositories.EstadoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class EstadoService extends EntityService<EstadoModel>{

    private final EstadoRepository estadoRepository;

    EstadoService(JpaRepository<EstadoModel, Long> repository, EstadoRepository estadoRepository) {
        super(repository);
        this.estadoRepository = estadoRepository;
    }


    public EstadoModel findBynome(String nome) {
        return ((EstadoRepository) repository).findBynomeContainingIgnoreCase(nome);
    }

    public EstadoModel findBysigla(String sigla) {
        return ((EstadoRepository) repository).findBysiglaContainingIgnoreCase(sigla);
    }

}
