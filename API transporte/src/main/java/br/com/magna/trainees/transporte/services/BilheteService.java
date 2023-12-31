package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.repositories.BilheteRepository;

@Service
public class BilheteService extends EntityService<BilheteModel> {

	private static final Logger log = LoggerFactory.getLogger(BilheteService.class);

	private final BilheteRepository bilheteRepository;

	BilheteService(JpaRepository<BilheteModel, Long> repository, BilheteRepository bilheteRepository) {
		super(repository);
		this.bilheteRepository = bilheteRepository;
	}

	public BilheteModel gerarBilhete() {
		BilheteModel bilhete = new BilheteModel();
		log.info("Gerando novo Bilhete");
		return repository.save(bilhete);
	}

	public BilheteModel utilizarBilhete(BilheteModel bilheteModel) {
		try {
			Optional<BilheteModel> bilheteOptional = bilheteRepository.findById(bilheteModel.getId());
			BilheteModel bilhete = bilheteOptional.get();

			if (bilhete.isUtilizado())
				throw new RuntimeException("Bilhete já utilizado! Compre outro!");

			bilhete.setUtilizado(true);
			log.info("Utilizando o Bilhete do ID: " + bilheteModel.getId());
			return repository.save(bilhete);
		} catch (Exception e) {
			log.error("Erro ao utilizar o bilhete: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
