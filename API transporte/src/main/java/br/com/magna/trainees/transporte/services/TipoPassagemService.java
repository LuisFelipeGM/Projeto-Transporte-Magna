package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.excptions.TipoPassagemInvalidaException;
import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.TipoPassagemModel;


@Service
public class TipoPassagemService extends EntityService<TipoPassagemModel> {

	private static final Logger log = LoggerFactory.getLogger(TipoPassagemService.class);

	
	private final CartaoService cartaoService;
	
	private final BilheteService bilheteService;
	
	public TipoPassagemService(JpaRepository<TipoPassagemModel, Long> repository,
			 CartaoService cartaoService, BilheteService bilheteService) {
		super(repository);
		this.cartaoService = cartaoService;
		this.bilheteService = bilheteService;
	}

	public TipoPassagemModel adicionaTipoPassagem(Long idBilhete, Long idCartao) {
	    try {
	        if ((idBilhete == null && idCartao == null) || (idBilhete != null && idCartao != null)) {
	            throw new TipoPassagemInvalidaException("Escolha apenas um tipo de passagem para utilizar, Cartão ou Bilhete");
	        }

	        TipoPassagemModel tipoPassagem = new TipoPassagemModel();
	        
	        if (idBilhete != null) {
	            Optional<BilheteModel> bilheteOptional = bilheteService.findById(idBilhete);
	            if (bilheteOptional.isPresent()) {
	                BilheteModel bilhete = bilheteOptional.get();
	                bilheteService.utilizarBilhete(bilhete);
	                tipoPassagem.setBilhete(bilhete);
	                log.info("Cadastrando novo Tipo Passagem (BILHETE)");
	            } else {
	                throw new TipoPassagemInvalidaException("Bilhete não encontrado");
	            }
	            
	        } else {
	            Optional<CartaoModel> cartaoOptional = cartaoService.findById(idCartao);
	            if (cartaoOptional.isPresent()) {
	                CartaoModel cartao = cartaoOptional.get();
	                tipoPassagem.setCartao(cartao);
	                log.info("Cadastrando novo Tipo Passagem (CARTÃO)");
	            } else {
	                throw new TipoPassagemInvalidaException("Cartão não encontrado");
	            }
	            
	        }
	        
	        return repository.save(tipoPassagem);
	    } catch (TipoPassagemInvalidaException e) {
	        log.error("Erro ao criar o registro de TipoPassagem: " + e.getMessage());
	        throw e;
	    }
	}

	
	

}
