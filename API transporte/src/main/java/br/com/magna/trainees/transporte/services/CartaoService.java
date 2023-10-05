package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.dtos.CartaoDto;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.repositories.CartaoRepository;

@Service
public class CartaoService extends EntityService<CartaoModel> {

	private static final Logger log = LoggerFactory.getLogger(CartaoService.class);

	private final CartaoRepository cartaoRepository;
	private final PassageiroService passageiroService;

	public CartaoService(JpaRepository<CartaoModel, Long> repository, CartaoRepository cartaoRepository,
			PassageiroService passageiroService) {
		super(repository);
		this.cartaoRepository = cartaoRepository;
		this.passageiroService = passageiroService;
	}

	public CartaoModel adicionaCartao(CartaoDto cartaoDto) {
		try {
			Optional<PassageiroModel> passageiroOptional = passageiroService.findById(cartaoDto.idPassageiro());
			if (passageiroOptional.isPresent()) {
				PassageiroModel passageiro = passageiroOptional.get();

				if (passageiro.getCartao() != null)
					throw new RuntimeException(
							"O passageiro já possui um cartão. Não é permitido ter mais de um cartão.");

				CartaoModel cartao = new CartaoModel();
				BeanUtils.copyProperties(cartaoDto, cartao);
				cartao.setPassageiro(passageiro);
				if (cartao.getTipoPassageiro() == null)
					throw new RuntimeException("Tipo de Passageiro é Obrigatório!");

				log.info("Cadastrando novo Cartão");

				return repository.save(cartao);

			} else {
				throw new Exception("Passageiro não encontrado");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("Erro ao salvar o novo cartão: Restrição exclusiva violada.");
			throw new RuntimeException("Erro ao salvar o novo cartão: Restrição exclusiva violada.");
		} catch (Exception e) {
			log.error("Erro ao cadastrar novo Cartão: " + e.getMessage());
			throw new RuntimeException(e.getMessage());

		}
	}

	public CartaoModel putCartao(CartaoDto cartaoDto, Long id) {
		try {
			Optional<CartaoModel> cartaoOptional = cartaoRepository.findById(id);
			Optional<PassageiroModel> passageiroOptional = passageiroService.findById(cartaoDto.idPassageiro());
			if (passageiroOptional.isPresent()) {
				CartaoModel cartao = cartaoOptional.get();
				BeanUtils.copyProperties(cartaoDto, cartao);
				if (cartao.getTipoPassageiro() == null)
					throw new RuntimeException("Tipo de Passageiro é Obrigatório!");

				log.info("Atualizando Cartao de ID: " + id);
				return repository.save(cartao);
			} else {
				throw new RuntimeException("Passageiro não encontrado");
			}
		} catch (DataIntegrityViolationException e) {
			log.error("Erro ao atualizar o cartão: Restrição exclusiva violada.");
			throw new RuntimeException("Erro ao atualizar o cartão: Restrição exclusiva violada.");
		} catch (Exception e) {
			log.error("Erro ao atualizar o Cartão: " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
