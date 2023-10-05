package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.repositories.PassageiroRepository;

@Service
public class PassageiroService extends EntityService<PassageiroModel> {
	
	private static final Logger log = LoggerFactory.getLogger(PassageiroService.class);

	private final PassageiroRepository passageiroRepository;

	PassageiroService(JpaRepository<PassageiroModel, Long> repository, PassageiroRepository passageiroRepository) {
		super(repository);
		this.passageiroRepository = passageiroRepository;
	}

	public PassageiroModel adicionaPassageiro(PassageiroDto passageiroDto){
		try {
			PassageiroModel passageiro = new PassageiroModel();
			BeanUtils.copyProperties(passageiroDto, passageiro);
			if(passageiro.getDataNascimento() == null)
				throw new RuntimeException("A Data de Nascimento é Obrigatória!");

			log.info("Cadastrando novo Passageiro");

			return repository.save(passageiro);

		} catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar o novo passageiro: Restrição exclusiva violada.");
            throw new RuntimeException("Erro ao salvar o novo passageiro: Este CPF já está cadastrado no sistema.");
        } catch (Exception e) {
            log.error("Erro ao cadastrar novo passageiro: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
	}

	public PassageiroModel putPassageiro(PassageiroDto passageiroDto, Long id){
			try {
			Optional<PassageiroModel> passageiroOptional = passageiroRepository.findById(id);
				PassageiroModel pass = passageiroOptional.get();
                BeanUtils.copyProperties(passageiroDto, pass);
                if(pass.getDataNascimento() == null)
                	throw new RuntimeException("A Data de Nascimento é Obrigatória!");
                
                log.info("Atualizando Passageiro de ID: " + id);
                return repository.save(pass);
			} catch (Exception e) {
				log.error("Erro ao copiar as propriedades do DTO para o modelo de Passageiro: " + e.getMessage());
            	throw new RuntimeException(e.getMessage());
			}




	}

}
