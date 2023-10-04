package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.dtos.LinhaDto;
import br.com.magna.trainees.transporte.models.LinhaModel;
import br.com.magna.trainees.transporte.repositories.LinhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinhaService extends EntityService<LinhaModel>{

    private static final Logger log = LoggerFactory.getLogger(LinhaService.class);

    private final LinhaRepository linhaRepository;

    LinhaService(JpaRepository<LinhaModel, Long> repository, LinhaRepository linhaRepository) {
        super(repository);
        this.linhaRepository = linhaRepository;
    }

    public LinhaModel adicionaLinha(LinhaDto linhaDto){
        try {
            LinhaModel linha = new LinhaModel();
            BeanUtils.copyProperties(linhaDto, linha);
            if(linha.getNumero() == 0)
            	throw new RuntimeException("Numero da Linha não pode ser 0 ou nulo");

            log.info("Cadastrando nova Linha");
            return repository.save(linha);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar o nova Linha: Restrição exclusiva violada.");
            throw new RuntimeException("Erro ao salvar o nova Linha: Este Numero de Linha já está cadastrado no sistema.");
        }catch (Exception e){
            log.error("Erro ao copiar as propriedades do DTO para o modelo de Linha" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public LinhaModel putLinha(LinhaDto linhaDto, Long id){
        try {
            Optional<LinhaModel> linhaOptional = linhaRepository.findById(id);
            if (linhaOptional.isPresent()){
                LinhaModel linha = linhaOptional.get();
                BeanUtils.copyProperties(linhaDto, linha);
                if(linha.getNumero() == 0)
                	throw new RuntimeException("Numero da Linha não pode ser 0 ou nulo");
                
                log.info("Atualizando a Linha de ID: " + id);
                return repository.save(linha);
            } else {
                throw new RuntimeException("Erro ao atualizar a Linha");
            }
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar o nova Linha: Restrição exclusiva violada.");
            throw new RuntimeException("Erro ao salvar o nova Linha: Este Numero de Linha já está cadastrado no sistema.");
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
