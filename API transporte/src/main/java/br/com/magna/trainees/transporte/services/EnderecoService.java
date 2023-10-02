package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.dtos.EnderecoDto;
import br.com.magna.trainees.transporte.models.BairroModel;
import br.com.magna.trainees.transporte.models.EnderecoModel;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.repositories.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService extends EntityService<EnderecoModel> {

	private static final Logger log = LoggerFactory.getLogger(EnderecoService.class);

	private final EnderecoRepository enderecoRepository;

	private final BairroService bairroService;

	private final EstacaoService estacaoService;

	public EnderecoService(JpaRepository<EnderecoModel, Long> repository, EnderecoRepository enderecoRepository,
			BairroService bairroService, EstacaoService estacaoService) {
		super(repository);
		this.enderecoRepository = enderecoRepository;
		this.bairroService = bairroService;
		this.estacaoService = estacaoService;
	}

	public EnderecoModel adicionarEndereco(EnderecoDto enderecoDto) {
		try {
			Optional<EstacaoModel> estacaoOptional = estacaoService.findById(enderecoDto.idEstacao());
			if (estacaoOptional.isPresent()) {
				EstacaoModel estacao = estacaoOptional.get();
				Optional<BairroModel> bairroOptional = bairroService.findById(enderecoDto.idBairro());

				if (bairroOptional.isPresent()) {
					BairroModel bairro = bairroOptional.get();
					EnderecoModel endereco = new EnderecoModel();
					BeanUtils.copyProperties(enderecoDto, endereco);
					endereco.setBairro(bairro);
					endereco.setEstacao(estacao);

					log.info("Cadastrando novo Endereço");
					return repository.save(endereco);
				} else {
					throw new Exception("Bairro não encontrado");
				}
			} else {
				throw new Exception("Estação não encontrada");
			}
		} catch (Exception e) {
			log.error("Erro ao salvar o Bairro: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	public EnderecoModel putEndereco(EnderecoDto enderecoDto, Long id) {
		try {
			Optional<EnderecoModel> enderecoOptional = enderecoRepository.findById(id);
			if (enderecoOptional.isPresent()) {
				EnderecoModel endereco = enderecoOptional.get();
				Optional<EstacaoModel> estacaoOptional = estacaoService.findById(enderecoDto.idEstacao());

				if (estacaoOptional.isPresent()) {
					EstacaoModel estacao = estacaoOptional.get();
					Optional<BairroModel> bairroOptional = bairroService.findById(enderecoDto.idBairro());

					if (bairroOptional.isPresent()) {
						BairroModel bairro = bairroOptional.get();
						BeanUtils.copyProperties(enderecoDto, endereco);
						endereco.setBairro(bairro);
						endereco.setEstacao(estacao);

						log.info("Atualizando Endereço de ID " + id);
						return repository.save(endereco);
					} else {
						throw new Exception("Bairro não encontrado");
					}
				} else {
					throw new Exception("Estação não encontrada");
				}
			} else {
				throw new Exception("Endereço não encontrado");
			}
		} catch (Exception e) {
			log.error("Erro ao atualizar o Endereço: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
