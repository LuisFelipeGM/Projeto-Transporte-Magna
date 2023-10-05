package br.com.magna.trainees.transporte.controllers;

import java.sql.Time;
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

import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.repositories.EstacaoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EstacaoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private EstacaoRepository estacaoRepository;

	@BeforeEach
	public void inicializar() {
		criarEstacao();
	}

	@AfterEach
	public void finalizar() {
		estacaoRepository.LimparDadosERedefinirSequence();
	}

	public void criarEstacao() {
		EstacaoDto estacao = new EstacaoDto("Pinheiros", null, null);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/estacao/", estacao, EstacaoModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar uma Estação já que as informações estão validas")
	public void testCadastrarUmaEstacaoComInformacoesValidas() {
		EstacaoDto estacao = new EstacaoDto("Faria Lima", Time.valueOf("12:30:00"), Time.valueOf("20:00:00"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacao, headers);

		ResponseEntity<EstacaoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/estacao/", request, EstacaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria cadastrar uma Estação já que se o horario de abertura estiver nula usara o padrão")
	public void testCadastrarUmaEstacaoComHorarioAberturaNulo() {
		EstacaoDto estacao = new EstacaoDto("Faria Lima", null, Time.valueOf("20:00:00"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacao, headers);

		ResponseEntity<EstacaoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/estacao/", request, EstacaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria cadastrar uma Estação já que se o horario de fechamento estiver nula usara o padrão")
	public void testCadastrarUmaEstacaoComHorarioFechamentoNulo() {
		EstacaoDto estacao = new EstacaoDto("Faria Lima", Time.valueOf("12:30:00"), null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacao, headers);

		ResponseEntity<EstacaoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/estacao/", request, EstacaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria cadastrar uma Estação já que se os horarios estiverem nulos usara o padrão")
	public void testCadastrarUmaEstacaoComHorariosNulos() {
		EstacaoDto estacao = new EstacaoDto("Faria Lima", null, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacao, headers);

		ResponseEntity<EstacaoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/estacao/", request, EstacaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Estação já que seu nome não pode ser nulo")
	public void testCadastrarUmaEstacaoComNomeNulo() {
		EstacaoDto estacao = new EstacaoDto(null, Time.valueOf("12:30:00"), Time.valueOf("20:00:00"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacao, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/estacao/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("NOME : O nome da Estação é obrigatório"));
	}
	
	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Estação com valores invalidos")
	public void testCadastrarUmaEstacaoComDadosInvalidos() {
		EstacaoDto estacao = new EstacaoDto("", null, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/estacao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria Listar todas as estações")
	public void testListarTodasAsEstacoesDoSistema() {
		ResponseEntity<List<EstacaoModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EstacaoModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar a estação com id valido")
	public void testListarUmaEstacaoComIdValido() {
		ResponseEntity<EstacaoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/estacao/1", EstacaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou a estação de id invalido")
	public void testListarUmaEstacaoComIdInvalido() {
		ResponseEntity<EstacaoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/estacao/1000", EstacaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retornar a estação de ID 1 alterado com sucesso")
	public void testAtualizarUmaEstacaoComIdValido() {
		EstacaoDto estacaoAtualizada = new EstacaoDto("Barra Funda", Time.valueOf("12:00:00"),
				Time.valueOf("23:00:00"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacaoAtualizada, headers);

		ResponseEntity<EstacaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/1", HttpMethod.PUT, request, EstacaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(estacaoAtualizada.nome(), response.getBody().getNome());
	}
	
	@Test
	@DisplayName("Deveria retornar a estação de ID 1 alterado com sucesso já que o horario de abertura nulo pegara o valor padrão")
	public void testAtualizarUmaEstacaoComHorarioAberturaNulo() {
		EstacaoDto estacaoAtualizada = new EstacaoDto("Barra Funda", null,
				Time.valueOf("23:00:00"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacaoAtualizada, headers);

		ResponseEntity<EstacaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/1", HttpMethod.PUT, request, EstacaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(estacaoAtualizada.nome(), response.getBody().getNome());
	}
	
	@Test
	@DisplayName("Deveria retornar a estação de ID 1 alterado com sucesso já que o horario de fechamento nulo pegara o valor padrão")
	public void testAtualizarUmaEstacaoComHorarioFechamentoNulo() {
		EstacaoDto estacaoAtualizada = new EstacaoDto("Barra Funda", Time.valueOf("12:00:00"),
				null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacaoAtualizada, headers);

		ResponseEntity<EstacaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/1", HttpMethod.PUT, request, EstacaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(estacaoAtualizada.nome(), response.getBody().getNome());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar uma estação já que seu nome não pode ser nulo")
	public void testAtualizarUmaEstacaoComNomeNulo() {
		EstacaoDto estacaoAtualizada = new EstacaoDto(null, Time.valueOf("12:00:00"),
				Time.valueOf("23:00:00"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EstacaoDto> request = new HttpEntity<>(estacaoAtualizada, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/1", HttpMethod.PUT, request, JsonNode.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	// TESTES DELETE
	
	@Test
	@DisplayName("Deveria retornar no Content já que a estação foi excluido com sucesso")
	public void testDeletaUmaEstacaoComIdValido() {
		ResponseEntity<EstacaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/1", HttpMethod.DELETE, null, EstacaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria retornar not found já que a estação não foi encontrada")
	public void testDeletaUmaEstacaoComIdInvalido() {
		ResponseEntity<EstacaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/estacao/100", HttpMethod.DELETE, null, EstacaoModel.class);
		
		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
