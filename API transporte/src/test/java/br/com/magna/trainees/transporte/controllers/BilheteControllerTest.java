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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.magna.trainees.transporte.models.BilheteModel;
import br.com.magna.trainees.transporte.repositories.BilheteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BilheteControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private BilheteRepository bilheteRepository;

	@BeforeEach
	public void inicializar() {
		criarBilhete();
	}

	@AfterEach
	public void finalizar() {
		bilheteRepository.LimparDadosERedefinirSequence();
	}

	public void criarBilhete() {
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/bilhete/", null, BilheteModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria criar um bilhete")
	public void testCriarBilhete() {
		ResponseEntity<BilheteModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/bilhete/", null, BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os bilhetes")
	public void testListarTodosOsBilhetesDoSistema() {
		ResponseEntity<List<BilheteModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bilhete/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BilheteModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria listar o bilhete de id valido")
	public void testListarUmBilheteComIdValido() {
		ResponseEntity<BilheteModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/bilhete/1", BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o bilhete de id invalido")
	public void testListarUmBilheteComIdInvalido() {
		ResponseEntity<BilheteModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/bilhete/100", BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retornar o bilhete de ID 1 alterado com sucesso com o campo Usado com TRUE")
	public void testUtilizarBilheteComIdValido() {
		ResponseEntity<BilheteModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bilhete/1", HttpMethod.PUT, null, BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertTrue(response.getBody().isUtilizado());
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar um passageiro já que o Id do Bilhete é invalido")
	public void testUtilizarBilheteComIdInvalido() {
		ResponseEntity<BilheteModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bilhete/100", HttpMethod.PUT, null, BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro por tentar utilizar um bilhete usado")
	public void testUtilizarBilheteUsado() {
		restTemplate.exchange("http://localhost:" + randomServerPort + "/bilhete/1", HttpMethod.PUT, null,
				BilheteModel.class);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bilhete/1", HttpMethod.PUT, null, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que o bilhete foi excluido com sucesso")
	public void testDeletaUmPassageiroComIdValido() {
		ResponseEntity<BilheteModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bilhete/1", HttpMethod.DELETE, null, BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que o bilhete não foi encontrado")
	public void testDeletaUmPassageiroComIdInvalido() {
		ResponseEntity<BilheteModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bilhete/100", HttpMethod.DELETE, null, BilheteModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
