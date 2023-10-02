package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.dtos.BairroDto;
import br.com.magna.trainees.transporte.models.BairroModel;
import br.com.magna.trainees.transporte.models.CidadeModel;
import br.com.magna.trainees.transporte.repositories.BairroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BairroService extends EntityService<BairroModel> {

	private static final Logger log = LoggerFactory.getLogger(BairroService.class);

	private final BairroRepository bairroRepository;
	private final CidadeService cidadeService;

	public BairroService(JpaRepository<BairroModel, Long> repository, BairroRepository bairroRepository,
			CidadeService cidadeService) {
		super(repository);
		this.bairroRepository = bairroRepository;
		this.cidadeService = cidadeService;
	}

	public BairroModel adicionaBairro(BairroDto bairroDto) {
		try {
			Optional<CidadeModel> cidadeOptional = cidadeService.findById(bairroDto.idCidade());
			if (cidadeOptional.isPresent()) {
				CidadeModel cidade = cidadeOptional.get();
				BairroModel bairro = new BairroModel();
				BeanUtils.copyProperties(bairroDto, bairro);
				bairro.setCidade(cidade);

				log.info("Cadastrando novo Bairro");
				return repository.save(bairro);
			} else {
				throw new Exception("Cidade não encontrada");
			}
		} catch (Exception e) {
			log.error("Erro ao salvar o Bairro: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public BairroModel putBairro(BairroDto bairroDto, Long id) {
		try {
			Optional<BairroModel> bairroOptional = bairroRepository.findById(id);
			if (bairroOptional.isPresent()) {
				BairroModel bairro = bairroOptional.get();
				Optional<CidadeModel> cidadeOptional = cidadeService.findById(bairroDto.idCidade());

				if (cidadeOptional.isPresent()) {
					CidadeModel cidade = cidadeOptional.get();
					BeanUtils.copyProperties(bairroDto, bairro);
					bairro.setCidade(cidade);

					log.info("Atualizando Bairro de ID " + id);
					return repository.save(bairro);
				} else {
					throw new Exception("Cidade não encontrada");
				}

			} else {
				throw new Exception("Bairro não encontrado");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public BairroModel findByNome(String nome) {
		return ((BairroRepository) repository).findBynomeContainingIgnoreCase(nome);
	}

}
