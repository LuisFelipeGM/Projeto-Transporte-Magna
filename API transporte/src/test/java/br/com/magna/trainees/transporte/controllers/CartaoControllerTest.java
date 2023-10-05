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
import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.enums.TipoPassageiro;
import br.com.magna.trainees.transporte.models.CartaoModel;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.repositories.CartaoRepository;
import br.com.magna.trainees.transporte.repositories.PassageiroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CartaoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private PassageiroRepository passageiroRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	@BeforeEach
	public void inicializar() {
		criarPassageiro();
	}

	@AfterEach
	public void finalizar() {
		cartaoRepository.LimparDadosERedefinirSequence();
		passageiroRepository.LimparDadosERedefinirSequence();
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

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Teste já que suas informações estão validas")
	public void testCadastrarUmCartaoComInformacoesValidas() {
		CartaoDto cartao = new CartaoDto(12345960l, TipoPassageiro.BILHETE_UNICO, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<CartaoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, CartaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Cartão já que seu numero não pode ser nulo")
	public void testCadastrarUmCartaoComNumeroNulo() {
		CartaoDto cartao = new CartaoDto(null, TipoPassageiro.BILHETE_UNICO, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("NUMERO : O Número do cartão é obrigatório"));

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Cartão já que seu tipo passageiro não pode ser nulo")
	public void testCadastrarUmCartaoComTipoPassageiroNulo() {
		CartaoDto cartao = new CartaoDto(12345960l, null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Cartão já que o Id do passageiro não pode ser nulo")
	public void testCadastrarUmCartaoComIdPassageiroNulo() {
		CartaoDto cartao = new CartaoDto(12345960l, TipoPassageiro.BILHETE_UNICO, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDPASSAGEIRO : O Id do Passageiro é obrigatório"));

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Cartão já que seu numero já existe no sistema")
	public void testCadastrarUmCartaoComNumeroUnique() {
		criarCartao();
		PassageiroDto passageiro = new PassageiroDto("Luís Felipe", "13256754314", LocalDate.of(2003, 9, 01));
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", passageiro,
				PassageiroModel.class);
		CartaoDto cartao = new CartaoDto(12345990l, TipoPassageiro.BILHETE_UNICO, 2l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Cartão já que o Id do passageiro é inválido")
	public void testCadastrarUmCartaoComIdUsuarioInvalido() {
		CartaoDto cartao = new CartaoDto(12345990l, TipoPassageiro.BILHETE_UNICO, 100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Cartão se o passageiro já tiver um cartão")
	public void testCadastrarUmCartaoComIdUsuarioJaTendoUmCartao() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(12345990l, TipoPassageiro.BILHETE_UNICO, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/cartao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}
	
	

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os cartões")
	public void testListarTodosOsCartoesDoSistema() {
		criarCartao();
		ResponseEntity<List<CartaoModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CartaoModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar o cartão de id valido")
	public void testListarUmCartaoComIdValido() {
		criarCartao();
		ResponseEntity<CartaoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/cartao/1", CartaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o cartão de id invalido")
	public void testListarUmCartaoComIdInvalido() {
		criarCartao();
		ResponseEntity<CartaoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/cartao/100", CartaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retornar o cartao de ID 1 alterado com sucesso")
	public void testAtualizarUmCartaoComIdValido() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(109954321l, TipoPassageiro.PASSE_LIVRE, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<CartaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.PUT, request,
				CartaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(cartao.tipoPassageiro(), response.getBody().getTipoPassageiro());
		
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um cartao já que seu numero não pode ser nulo")
	public void testAtualizarUmCartaoComNumeroNulo() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(null, TipoPassageiro.PASSE_LIVRE, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um cartao já que seu tipo passagem não pode ser nulo")
	public void testAtualizarUmCartaoComTipoPassageiro() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(109954321l, null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um cartao já que seu id do usuario não pode ser nulo")
	public void testAtualizarUmCartaoComIdPassageiroNulo() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(109954321l, TipoPassageiro.PASSE_LIVRE, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um cartão já que o Id do passageiro é inválido")
	public void testAtualizarUmCartaoComIdPassageiroInvalido() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(109954321l, TipoPassageiro.PASSE_LIVRE, 100l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um cartão que seu numero já esteja no sistema")
	public void testAtualizarUmCartaoComNumeroUnique() {
		criarCartao();
		PassageiroDto passageiro = new PassageiroDto("Luís Felipe", "13245678901", LocalDate.of(2003, 9, 01));
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", passageiro,
				PassageiroModel.class);
		
		CartaoDto cartao = new CartaoDto(123456654l, TipoPassageiro.BILHETE_UNICO, 2l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/cartao/", cartao, CartaoModel.class);
		
		
		CartaoDto cartaoAtualizado = new CartaoDto(123456654l, TipoPassageiro.PASSE_LIVRE, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartaoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um cartão que o Id é inválido")
	public void testAtualizarUmCartaoComIdInvalido() {
		criarCartao();
		CartaoDto cartao = new CartaoDto(109954321l, TipoPassageiro.PASSE_LIVRE, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CartaoDto> request = new HttpEntity<>(cartao, headers);

		ResponseEntity<CartaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/100", HttpMethod.PUT, request,
				CartaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
		
	}
	
	// TESTES DELETE
	
	@Test
	@DisplayName("Deveria retornar no Content já que o cartão foi excluido com sucesso")
	public void testDeletaUmCartaoComIdValido() {
		criarCartao();
		ResponseEntity<CartaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/1", HttpMethod.DELETE, null,
				CartaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que o cartão não foi encontrado")
	public void testDeletaUmCartaoComIdInvalido() {
		criarCartao();
		ResponseEntity<CartaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/cartao/100", HttpMethod.DELETE, null,
				CartaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
