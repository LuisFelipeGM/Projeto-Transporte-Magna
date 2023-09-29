package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.models.CidadeModel;
import br.com.magna.trainees.transporte.repositories.CidadeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CidadeService extends EntityService<CidadeModel> {

    private final CidadeRepository cidadeRepository;


    CidadeService(JpaRepository<CidadeModel, Long> repository, CidadeRepository cidadeRepository) {
        super(repository);
        this.cidadeRepository = cidadeRepository;
    }

    public CidadeModel findBynome(String nome) {
        return ((CidadeRepository) repository).findBynomeContainingIgnoreCase(nome);
    }

}
