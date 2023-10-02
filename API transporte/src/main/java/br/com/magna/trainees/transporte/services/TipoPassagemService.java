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
import br.com.magna.trainees.transporte.repositories.BilheteRepository;
import br.com.magna.trainees.transporte.repositories.CartaoRepository;
import br.com.magna.trainees.transporte.repositories.TipoPassagemRepository;


@Service
public class TipoPassagemService extends EntityService<TipoPassagemModel> {

	private static final Logger log = LoggerFactory.getLogger(TipoPassagemService.class);

	private final TipoPassagemRepository tipoPassagemRepository;
	
	private final CartaoService cartaoService;
	
	private final BilheteService bilheteService;
	
	public TipoPassagemService(JpaRepository<TipoPassagemModel, Long> repository,
			TipoPassagemRepository tipoPassagemRepository, CartaoService cartaoService, BilheteService bilheteService) {
		super(repository);
		this.tipoPassagemRepository = tipoPassagemRepository;
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
	            
	        } else if (idCartao != null) {
	            Optional<CartaoModel> cartaoOptional = cartaoService.findById(idCartao);
	            if (cartaoOptional.isPresent()) {
	                CartaoModel cartao = cartaoOptional.get();
	                tipoPassagem.setCartao(cartao);
	                log.info("Cadastrando novo Tipo Passagem (CARTÃO)");
	            } else {
	                throw new TipoPassagemInvalidaException("Cartão não encontrado");
	            }
	            
	        } else {
	            throw new TipoPassagemInvalidaException("Nenhum tipo de passagem especificado");
	        }
	        
	        return repository.save(tipoPassagem);
	    } catch (TipoPassagemInvalidaException e) {
	        log.error("Erro ao criar o registro de TipoPassagem: " + e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        log.error("Erro ao criar o registro de TipoPassagem: " + e.getMessage());
	        throw new RuntimeException(e.getMessage());
	    }
	}

	
	

}
