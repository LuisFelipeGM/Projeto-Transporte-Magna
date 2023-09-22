package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.dtos.CartaoDto;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.repositories.CartaoRepository;
import br.com.magna.trainees.transporte.repositories.PassageiroRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartaoService extends EntityService<CartaoModel> {

    private final CartaoRepository cartaoRepository;
    private final PassageiroRepository passageiroRepository;

    CartaoService(JpaRepository<CartaoModel, Long> repository, CartaoRepository cartaoRepository, PassageiroRepository passageiroRepository) {
        super(repository);
        this.cartaoRepository = cartaoRepository;
        this.passageiroRepository = passageiroRepository;
    }

    public CartaoModel adicionaCartao(CartaoDto cartaoDto){
        try {
            Optional<PassageiroModel> passageiroOptional = passageiroRepository.findById(cartaoDto.getIdPassageiro());
            if (passageiroOptional.isPresent()) {
                PassageiroModel passageiro = passageiroOptional.get();
                CartaoModel cartao =  new CartaoModel();
                BeanUtils.copyProperties(cartaoDto, cartao);
                cartao.setPassageiro(passageiro);
        
                log.info("Cadastrando novo Cartão");
        
                return repository.save(cartao);
                
            } else {
                throw new Exception("Passageiro não encontrado");
            }
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar o novo cartão: Restrição exclusiva violada.");
            throw new RuntimeException("Erro ao salvar o novo cartão!");
        } catch (Exception e) {
            log.error("Erro ao copiar as propriedades do DTO para o modelo de Cartão: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar o novo cartão.");

        }
    }

        public CartaoModel putCartao(CartaoDto cartaoDto, Long id){
            try {
                Optional<CartaoModel> cartaoOptional = cartaoRepository.findById(id);
               if (cartaoOptional.isPresent()) {
                    CartaoModel cartao = cartaoOptional.get();
                    BeanUtils.copyProperties(cartaoDto, cartao);

                    log.info("Atualizando Cartao de ID: " + id);
                    return repository.save(cartao);
               } else {
                    throw new RuntimeException("Erro ao atualizar o Cartão.");
               }
            } catch (Exception e) {
				log.error("Erro ao copiar as propriedades do DTO para o modelo de Cartão: " + e.getMessage());
            	throw new RuntimeException("Erro ao atualizar o Cartão.");
			}
        }


    
}
