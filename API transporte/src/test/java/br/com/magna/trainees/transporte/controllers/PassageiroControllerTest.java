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

import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.repositories.PassageiroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PassageiroControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private PassageiroRepository passageiroRepository;

	@BeforeEach
	public void inicializar() {
		criarPassageiro();
	}

	@AfterEach
	public void finalizar() {
		passageiroRepository.LimparDadosERedefinirSequence();
	}

	public void criarPassageiro() {
		PassageiroDto passageiro = new PassageiroDto("Luís Felipe", "12345678901", LocalDate.of(2003, 9, 01));
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", passageiro,
				PassageiroModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Passageiro já que suas informações estão validas")
	public void testCadastrarUmPassageiroComInformacoesValidas() {
		PassageiroDto passageiro = new PassageiroDto("Soren Bjergsen", "12345678990", LocalDate.of(2003, 9, 01));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiro, headers);

		ResponseEntity<PassageiroModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", request, PassageiroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Passageiro já que seu nome não pode ser nulo")
	public void testCadastrarUmPassageiroComNomeNulo() {
		PassageiroDto passageiro = new PassageiroDto(null, "12345678990", LocalDate.of(2003, 9, 01));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiro, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("NOME : O nome é obrigatório"));

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Passageiro já que seu CPF não pode ser nulo")
	public void testCadastrarUmPassageiroComCPFNulo() {
		PassageiroDto passageiro = new PassageiroDto("Soren Bjergsen", null, LocalDate.of(2003, 9, 01));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiro, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("CPF : CPF é obrigatório"));

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Passageiro já que sua Data de Nascimento não pode ser nula")
	public void testCadastrarUmPassageiroComADataDeNascimentoNula() {
		PassageiroDto passageiro = new PassageiroDto("Soren Bjergsen", "12345678990", null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiro, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Passageiro já que o CPF já existe no sistema")
	public void testCadastrarUmPassageiroComACPFUnique() {
		PassageiroDto passageiro = new PassageiroDto("Soren Bjergsen", "12345678901", LocalDate.of(2003, 9, 01));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiro, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/passageiro/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);

	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os passageiros")
	public void testListarTodosOsPassageirosDoSistema() {
		ResponseEntity<List<PassageiroModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PassageiroModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria listar o passageiro de id valido")
	public void testListarUmPassageiroComIdValido() {
		ResponseEntity<PassageiroModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/passageiro/1", PassageiroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o passageiro de id invalido")
	public void testListarUmPassageiroComIdInvalido() {
		ResponseEntity<PassageiroModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/passageiro/100", PassageiroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retornar o passageiro de ID 1 alterado com sucesso")
	public void testAtualizarUmPassageiroComIdValido() {
		PassageiroDto passageiroAtualizado = new PassageiroDto("James Jensen", "12345098901",
				LocalDate.of(2000, 9, 04));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiroAtualizado, headers);

		ResponseEntity<PassageiroModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/1", HttpMethod.PUT, request,
				PassageiroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(passageiroAtualizado.nome(), response.getBody().getNome());
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um passageiro já que seu nome não pode ser nulo")
	public void testAtualizarUmPassageiroComNomeNulo() {
		PassageiroDto passageiroAtualizado = new PassageiroDto(null, "12345098901", LocalDate.of(2000, 9, 04));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiroAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um passageiro já que seu CPF não pode ser nulo")
	public void testAtualizarUmPassageiroComCPFNulo() {
		PassageiroDto passageiroAtualizado = new PassageiroDto("James Jensen", null, LocalDate.of(2000, 9, 04));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiroAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um passageiro já que sua data de nascimento não pode ser nula")
	public void testAtualizarUmPassageiroComDataDeNascimentoNula() {
		PassageiroDto passageiroAtualizado = new PassageiroDto("James Jensen", "12345098901", null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiroAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um passageiro já que o Id do Passageiro é invalido")
	public void testAtualizarUmPassageiroComIdInvalido() {
		PassageiroDto passageiroAtualizado = new PassageiroDto("Jensen James", "12345098901",
				LocalDate.of(2000, 9, 04));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PassageiroDto> request = new HttpEntity<>(passageiroAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/1000", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que o passageiro foi excluido com sucesso")
	public void testDeletaUmPassageiroComIdValido() {
		ResponseEntity<PassageiroModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/1", HttpMethod.DELETE, null,
				PassageiroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que o passageiro não foi encontrado")
	public void testDeletaUmPassageiroComIdInvalido() {
		ResponseEntity<PassageiroModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/passageiro/100", HttpMethod.DELETE, null,
				PassageiroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
