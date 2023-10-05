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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.magna.trainees.transporte.dtos.CartaoDto;
import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.dtos.ViagemDto;
import br.com.magna.trainees.transporte.enums.TipoPassageiro;
import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.models.PassageiroModel;
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
public class ViagemControllerTest {
	
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
	
	// TESTES POST
	
	@Test
	@DisplayName("Deveria cadastrar uma viagem já que suas informações estão validas")
	public void testCadastrarUmaViagemComUmCartao() {
		ViagemDto viagem = new ViagemDto(null, 1l, 1l);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ViagemDto> request = new HttpEntity<>(viagem, headers);

		ResponseEntity<ViagemModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/viagem/", request, ViagemModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria cadastrar uma viagem já que suas informações estão validas")
	public void testCadastrarUmaViagemComUmBilhete() {
		ViagemDto viagem = new ViagemDto(1l, null, 1l);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ViagemDto> request = new HttpEntity<>(viagem, headers);

		ResponseEntity<ViagemModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/viagem/", request, ViagemModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma viagem já que é necessario escolher um tipo de passagem")
	public void testCadastrarUmaViagemComBilheteECartaoNulos() {
		ViagemDto viagem = new ViagemDto(null, null, 1l);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ViagemDto> request = new HttpEntity<>(viagem, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/viagem/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma viagem já que é necessario escolher APENAS um tipo de passagem")
	public void testCadastrarUmaViagemComBilheteECartaoPrenchidos() {
		ViagemDto viagem = new ViagemDto(1l, 1l, 1l);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ViagemDto> request = new HttpEntity<>(viagem, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/viagem/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma viagem já que o Id da estação não pode ser nula")
	public void testCadastrarUmCartaoComNumeroNulo() {
		ViagemDto viagem = new ViagemDto(1l, null, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ViagemDto> request = new HttpEntity<>(viagem, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/viagem/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDESTACAO : O Id da Estação é obrigatório"));

	}
	
	// TESTES GET
	
	@Test
	@DisplayName("Deveria listar todos as viagens")
	public void testListarTodosAsViagensDoSistema() {
		criarViagem();
		ResponseEntity<List<ViagemModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/viagem/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ViagemModel>>() {});
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria listar a viagem de id valido")
	public void testListarUmaViagemComIdValido() {
		criarViagem();
		ResponseEntity<ViagemModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/viagem/1", ViagemModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria dar not found pois não achou uma viagem de id invalido")
	public void testListarUmaViagemComIdInvalido() {
		criarViagem();
		ResponseEntity<ViagemModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/viagem/100", ViagemModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	// TESTES DELETE
	
	@Test
	@DisplayName("Deveria retornar no Content já que a viagem foi excluida com sucesso")
	public void testDeletaUmaViagemComIdValido() {
		criarViagem();
		ResponseEntity<ViagemModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/viagem/1", HttpMethod.DELETE, null,
				ViagemModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que a viagem não foi encontrada")
	public void testDeletaUmaViagemComIdInvalido() {
		criarViagem();
		ResponseEntity<CartaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/viagem/100", HttpMethod.DELETE, null,
				CartaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	
	
	

}
