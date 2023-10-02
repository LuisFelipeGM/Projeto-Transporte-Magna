package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.dtos.ViagemDto;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.models.TipoPassagemModel;
import br.com.magna.trainees.transporte.models.ViagemModel;

@Service
public class ViagemService extends EntityService<ViagemModel> {

	private static final Logger log = LoggerFactory.getLogger(ViagemService.class);

	private final EstacaoService estacaoService;

	private final TipoPassagemService tipoPassagemService;

	public ViagemService(JpaRepository<ViagemModel, Long> repository, EstacaoService estacaoService,
			TipoPassagemService tipoPassagemService) {
		super(repository);
		this.estacaoService = estacaoService;
		this.tipoPassagemService = tipoPassagemService;
	}

	public ViagemModel adicionarViagem(ViagemDto viagemDto) {
		try {

			Optional<EstacaoModel> optionalEstacao = estacaoService.findById(viagemDto.idEstacao());
			if (optionalEstacao.isPresent()) {
				EstacaoModel estacao = optionalEstacao.get();

				log.info("Cadastrando novo Tipo Passagem");
				TipoPassagemModel tipoPassagem = tipoPassagemService.adicionaTipoPassagem(viagemDto.idBilhete(),
						viagemDto.idCartao());

				ViagemModel viagem = new ViagemModel();
				viagem.setEstacao(estacao);
				viagem.setTipoPassagem(tipoPassagem);

				log.info("Cadastrando nova Viagem");
				return repository.save(viagem);
			} else {
				throw new Exception("Estação não encontrada");
			}
		} catch (Exception e) {
			log.error("Erro ao adicionar nova viagem: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
