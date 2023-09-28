package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.dtos.TipoPassagemDto;
import br.com.magna.trainees.transporte.excptions.ValidacaoException;
import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.TipoPassagemModel;
import br.com.magna.trainees.transporte.repositories.BilheteRepository;
import br.com.magna.trainees.transporte.repositories.CartaoRepository;
import br.com.magna.trainees.transporte.repositories.TipoPassagemRepository;


@Service
public class TipoPassagemService extends EntityService<TipoPassagemModel> {

	private static final Logger log = LoggerFactory.getLogger(TipoPassagemService.class);

	private final TipoPassagemRepository tipoPassagemRepository;
	
	private final CartaoRepository cartaoRepository;
	
	private final BilheteRepository bilheteRepository;
	
	
	

	public TipoPassagemService(JpaRepository<TipoPassagemModel, Long> repository,
			TipoPassagemRepository tipoPassagemRepository, CartaoRepository cartaoRepository,
			BilheteRepository bilheteRepository) {
		super(repository);
		this.tipoPassagemRepository = tipoPassagemRepository;
		this.cartaoRepository = cartaoRepository;
		this.bilheteRepository = bilheteRepository;
	}
	
	public TipoPassagemModel adicionaTipoPassagem(TipoPassagemDto tipoPassagemDto) {
		try {
			
			if(tipoPassagemDto.idBilhete() == null && tipoPassagemDto.idCartao() == null)
				throw new ValidacaoException("Deve ser utilizado uma forma de "
						+ "passagem para realizar a viagem!");
			
			if(tipoPassagemDto.idBilhete() != null && tipoPassagemDto.idCartao() != null)
				throw new ValidacaoException("Escolha apenas um tipo de passagem para utilizar, "
						+ "Cartão ou Bilhete");
			
			Optional<CartaoModel> cartaoOptional = cartaoRepository.findById(tipoPassagemDto.idCartao());
			Optional<BilheteModel> bilheteOptional = bilheteRepository.findById(tipoPassagemDto.idBilhete());
			TipoPassagemModel tipoPassagem = new TipoPassagemModel();
			if(cartaoOptional.isPresent()) {
				CartaoModel cartao = cartaoOptional.get();
				tipoPassagem.setCartao(cartao);
				
				log.info("Cadastrando novo Tipo Passagem (CARTÃO)");
				
				return repository.save(tipoPassagem);
			}
			if(bilheteOptional.isPresent()) {
				BilheteModel bilhete = bilheteOptional.get();
				tipoPassagem.setBilhete(bilhete);
				
				log.info("Cadastrando novo Tipo Passagem (BILHETE)");
				
				return repository.save(tipoPassagem);
			}
			
			throw new RuntimeException("Tipo da Passagem informado não foi encontrado");
			
		} catch (Exception e) {
			log.error("Erro ao criar o registro de TipoPassagem: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
	}
	
	

}
