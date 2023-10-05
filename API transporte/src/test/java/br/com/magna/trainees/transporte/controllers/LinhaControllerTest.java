package br.com.magna.trainees.transporte.controllers;

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

import br.com.magna.trainees.transporte.dtos.LinhaDto;
import br.com.magna.trainees.transporte.models.LinhaModel;
import br.com.magna.trainees.transporte.repositories.LinhaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LinhaControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private LinhaRepository linhaRepository;

	@BeforeEach
	public void inicializar() {
		criarLinha();
	}

	@AfterEach
	public void finalizar() {
		linhaRepository.LimparDadosERedefinirSequence();
	}

	public void criarLinha() {
		LinhaDto linha = new LinhaDto("Amarela", 5);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/linha/", linha, LinhaModel.class);

	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar uma Linha já que suas informações estão validas")
	public void testCadastrarUmaLinhaComInformacoesValidas() {
		LinhaDto linha = new LinhaDto("Esmeralda", 9);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linha, headers);

		ResponseEntity<LinhaModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/linha/", request, LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Linha já que seu nome não pode ser nulo")
	public void testCadastrarUmaLinhaComNomeNulo() {
		LinhaDto linha = new LinhaDto(null, 9);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linha, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/linha/",
				request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("NOME : O nome da Linha é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Linha já que seu numero não pode ser nulo")
	public void testCadastrarUmaLinhaComNumeroNulo() {
		LinhaDto linha = new LinhaDto("Diamante", 0);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linha, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/linha/",
				request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Linha já que seu numero não pode ser repitido")
	public void testCadastrarUmaLinhaComNumeroRepetido() {
		LinhaDto linha = new LinhaDto("Amarela", 5);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linha, headers);

		ResponseEntity<JsonNode> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/linha/",
				request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		Assert.assertNotNull(response.getBody());
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todas as linhas")
	public void testListarTodasAsLinhasDoSistema() {
		ResponseEntity<List<LinhaModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/linha/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<LinhaModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar a linha de id valido")
	public void testListarUmaLinhaComIdValido() {
		ResponseEntity<LinhaModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/linha/1", LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou a linha de id invalido")
	public void testListarUmaLinhaComIdInvalido() {
		ResponseEntity<LinhaModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/linha/498", LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retornar a linha de ID 1 alterada com sucesso")
	public void testAtualizarUmaLinhaComIdValido() {
		LinhaDto linhaAtualizada = new LinhaDto("Verde", 9);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linhaAtualizada, headers);

		ResponseEntity<LinhaModel> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/linha/1",
				HttpMethod.PUT, request, LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(linhaAtualizada.nome(), response.getBody().getNome());
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar uma linha já que seu nome não pode ser nulo")
	public void testAtualizarUmaLinhaComNomeNulo() {
		LinhaDto linhaAtualizada = new LinhaDto(null, 9);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linhaAtualizada, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/linha/1",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar uma linha já que seu numero não pode ser nulo")
	public void testAtualizarUmaLinhaComNumeroNulo() {
		LinhaDto linhaAtualizada = new LinhaDto("Verde", 0);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linhaAtualizada, headers);

		ResponseEntity<LinhaModel> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/linha/1",
				HttpMethod.PUT, request, LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar uma linha já que o Id da Linha é invalido")
	public void testAtualizarUmaLinhaComIdLinhaInvalido() {
		LinhaDto linhaAtualizada = new LinhaDto("Verde", 19);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linhaAtualizada, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/linha/100",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar uma linha já que o numero da Linha é duplicado")
	public void testAtualizarUmaLinhaComIdUnique() {
		LinhaDto linha = new LinhaDto("Amarela", 2);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/linha/", linha, LinhaModel.class);
		LinhaDto linhaAtualizada = new LinhaDto("Verde", 2);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<LinhaDto> request = new HttpEntity<>(linhaAtualizada, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/linha/1",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que a linha foi excluida com sucesso")
	public void testDeletaUmaLinhaComIdValido() {
		ResponseEntity<LinhaModel> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/linha/1",
				HttpMethod.DELETE, null, LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que a linha não foi encontrada")
	public void testDeletaUmaLinhaComIdInvalido() {
		ResponseEntity<LinhaModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/linha/100", HttpMethod.DELETE, null, LinhaModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
