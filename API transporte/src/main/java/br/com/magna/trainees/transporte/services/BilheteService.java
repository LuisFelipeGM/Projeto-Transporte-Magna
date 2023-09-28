package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.excptions.ValidacaoException;
import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.repositories.BilheteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BilheteService extends EntityService<BilheteModel>{

	private final BilheteRepository bilheteRepository;
	
	BilheteService(JpaRepository<BilheteModel, Long> repository, BilheteRepository bilheteRepository) {
		super(repository);
		this.bilheteRepository = bilheteRepository;
	}

	
	public BilheteModel gerarBilhete() {
		try {
			BilheteModel bilhete = new BilheteModel();
			log.info("Gerando novo Bilhete");
			return repository.save(bilhete);
		} catch (Exception e) {
			log.error("Erro ao gerar um bilhete: " + e.getMessage());
            throw new RuntimeException("Erro ao gerar um bilhete.");
		}
	}
	
	public BilheteModel utilizarBilhete(Long id) {
		try {
			Optional<BilheteModel> bilheteOptional = bilheteRepository.findById(id);
			if(bilheteOptional.isPresent()) {
				BilheteModel bilhete = bilheteOptional.get();
				
				if (bilhete.isUtilizado() == true)
					throw new ValidacaoException("Bilhete já utilizado! Compre outro!");
				
				bilhete.setUtilizado(true);
				log.info("Utilizando o Bilhete do ID: " + id);
				return repository.save(bilhete);
			} else {
				throw new RuntimeException("Bilhete não encontrado");
			}
		} catch (Exception e) {
			log.error("Erro ao utilizar o bilhete: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
		} 
	}
	
}
