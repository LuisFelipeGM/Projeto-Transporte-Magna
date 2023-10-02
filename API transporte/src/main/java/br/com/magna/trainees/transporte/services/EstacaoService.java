package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.repositories.EstacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Optional;

@Service
public class EstacaoService extends EntityService<EstacaoModel>{

    private static final Logger log = LoggerFactory.getLogger(EstacaoService.class);
    private final EstacaoRepository estacaoRepository;

    EstacaoService(JpaRepository<EstacaoModel, Long> repository, EstacaoRepository estacaoRepository) {
        super(repository);
        this.estacaoRepository = estacaoRepository;
    }

    public EstacaoModel adicionaEstacao(EstacaoDto estacaoDto){
        try {
            EstacaoModel estacao = new EstacaoModel();
            BeanUtils.copyProperties(estacaoDto, estacao);
            if(estacao.getHorarioAbertura() == null)
                estacao.setHorarioAbertura(Time.valueOf("04:00:00"));

            if(estacao.getHorarioFechamento() == null)
                estacao.setHorarioFechamento(Time.valueOf("00:00:00"));

            log.info("Cadastrando nova Estação");
            return repository.save(estacao);
        } catch (Exception e) {
            log.error("Erro ao cadastrar nova Estação: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    public EstacaoModel putEstacao(EstacaoDto estacaoDto, Long id){
        try {
            Optional<EstacaoModel> estacaoOptional = estacaoRepository.findById(id);
            if (estacaoOptional.isPresent()){
                EstacaoModel estacao = estacaoOptional.get();
                BeanUtils.copyProperties(estacaoDto, estacao);
                if(estacao.getHorarioAbertura() == null)
                    estacao.setHorarioAbertura(Time.valueOf("04:00:00"));

                if(estacao.getHorarioFechamento() == null)
                    estacao.setHorarioFechamento(Time.valueOf("00:00:00"));

                log.info("Atualizando Estação de ID: " + id);
                return repository.save(estacao);
            } else {
                throw new RuntimeException("Erro ao atualizar a Estação.");
            }
        } catch (Exception e) {
            log.error("Erro ao copiar as propriedades do DTO para o modelo de Estacao: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
