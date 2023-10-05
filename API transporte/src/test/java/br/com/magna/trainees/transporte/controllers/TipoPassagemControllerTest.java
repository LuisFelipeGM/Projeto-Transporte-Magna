package br.com.magna.trainees.transporte.controllers;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.magna.trainees.transporte.dtos.CartaoDto;
import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.dtos.ViagemDto;
import br.com.magna.trainees.transporte.enums.TipoPassageiro;
import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.models.TipoPassagemModel;
import br.com.magna.trainees.transporte.models.ViagemModel;
import br.com.magna.trainees.transporte.repositories.BilheteRepository;
import br.com.magna.trainees.transporte.repositories.CartaoRepository;
import br.com.magna.trainees.transporte.repositories.EstacaoRepository;
import br.com.magna.trainees.transporte.repositories.PassageiroRepository;
import br.com.magna.trainees.transporte.repositories.TipoPassagemRepository;
import br.com.magna.trainees.transporte.repositories.ViagemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TipoPassagemControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private PassageiroRepository passageiroRepository;

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private TipoPassagemRepository tipoPassagemRepository;
	
	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private BilheteRepository bilheteRepository;
	
	@Autowired
	private EstacaoRepository estacaoRepository;
	
	@BeforeEach
	public void inicializar() {
		criarPassageiro();
		criarCartao();
		criarBilhete();
		criarEstacao();
		criarViagem();
	}

	@AfterEach
	public void finalizar() {
		viagemRepository.LimparDadosERedefinirSequence();
		tipoPassagemRepository.LimparDadosERedefinirSequence();
		bilheteRepository.LimparDadosERedefinirSequence();
		cartaoRepository.LimparDadosERedefinirSequence();
		passageiroRepository.LimparDadosERedefinirSequence();
		estacaoRepository.LimparDadosERedefinirSequence();
	}

	public void criarPassageiro() {
		PassageiroDto passageiro = new PassageiroDto("Luís Felipe", "12345678901", LocalDate.of(2003, 9, 01));
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", passageiro,
				PassageiroModel.class);
	}

	public void criarCartao() {
		CartaoDto cartao = new CartaoDto(12345990l, TipoPassageiro.BILHETE_UNICO, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/cartao/", cartao, CartaoModel.class);
	}
	
	public void criarBilhete() {
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/bilhete/", null, BilheteModel.class);
	}
	
	public void criarEstacao() {
		EstacaoDto estacao = new EstacaoDto("Pinheiros", null, null);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/estacao/", estacao, EstacaoModel.class);
	}
	
	public void criarViagem() {
		ViagemDto viagem = new ViagemDto(null, 1l, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/viagem/", viagem, ViagemModel.class);
	}
	
	// TESTES GET
	
	@Test
	@DisplayName("Deveria listar todos os tipos passagem")
	public void testListarTodosOsTiposPassagemDoSistema() {
		ResponseEntity<List<TipoPassagemModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/tipo-passagem/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoPassagemModel>>() {});
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria listar o tipo passagem de id valido")
	public void testListarUmTipoPassagemComIdValido() {
		criarViagem();
		ResponseEntity<TipoPassagemModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/tipo-passagem/1", TipoPassagemModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar not found pois não achou um tipo passagem de id invalido")
	public void testListarUmTipoPassagemComIdInvalido() {
		criarViagem();
		ResponseEntity<TipoPassagemModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/tipo-passagem/100", TipoPassagemModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	

}
