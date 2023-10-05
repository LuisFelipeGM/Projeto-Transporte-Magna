package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.dtos.BairroDto;
import br.com.magna.trainees.transporte.models.BairroModel;
import br.com.magna.trainees.transporte.repositories.BairroRepository;
import org.junit.jupiter.api.Test;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BairroControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private BairroRepository bairroRepository;

	private ResponseEntity<BairroModel> response;

	@Transactional
	@AfterEach
	public void redefinirSequenciaELimparDados() {
		bairroRepository.LimparDadosERedefinirSequence();
	}

	@BeforeEach
	public void inicializar() {
		BairroDto bairro = new BairroDto("Jardim Helena", 1l); 

		response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/bairro/", bairro,
				BairroModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Bairro já que suas informações estão validas")
	public void testCadastrandoUmBairroComIdCidadeValido() {

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Bairro já que seu nome não pode ser nulo")
	public void testCadastrandoUmBairroComNomeNulo() {
		BairroDto bairro = new BairroDto(null, 1L);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/bairro/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody); 
		Assert.assertTrue(responseBody.contains("NOME : O nome do bairro é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Bairro já que o Id da Cidade não pode ser nulo")
	public void testCadastrandoUmBairroComIdCidadeNulo() {
		BairroDto bairro = new BairroDto("Jardim Maria", null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/bairro/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDCIDADE : O Id da cidade é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Bairro já que o Id da Cidade é invalida")
	public void testCadastrandoUmBairroComIdCidadeInvalido() {
		BairroDto bairro = new BairroDto("Jardim Maria", 1000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/bairro/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todas os Bairros")
	public void testListarTodasOsBairrosDoSistema() {
		ResponseEntity<List<BairroModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bairro/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BairroModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);

		List<BairroModel> cidades = response.getBody();
		Assert.assertNotNull(cidades);

	}

	@Test
	@DisplayName("Deveria listar o bairro de id 1")
	public void testListarUmBairroComIdValido() {
		ResponseEntity<BairroModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/bairro/1", BairroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o bairro de id 200")
	public void testLitarUmBairroComIdInvalido() {
		ResponseEntity<BairroModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/bairro/200", BairroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria encontrar todos os bairro a partir do seu nome")
	public void testListarBairrosAPartirDoSeuNome() {
		ResponseEntity<List<BairroModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bairro/nome/Jardim Helena", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BairroModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());

	}
	
	@Test
	@DisplayName("Deveria dar not found pois não achou nenhum bairro a partir do nome")
	public void testListarBairrosAPartirDoSeuNomeInvalido() {
		ResponseEntity<List<BairroModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bairro/nome/Lima", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BairroModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());

	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retornar o bairro de ID 1 alterado com sucesso")
	public void testAtualizarUmBairroComIdValido() {
		BairroDto bairroAtualizado = new BairroDto("Jardim Novo", 1L);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairroAtualizado, headers);

		ResponseEntity<BairroModel> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/bairro/1",
				HttpMethod.PUT, request, BairroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar já que o nome é obrigatorio")
	public void testAtualizarUmBairroComNomeNulo() {
		BairroDto bairro = new BairroDto(null, 1L);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/bairro/1",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar já que o Id da Cidade é obrigatorio")
	public void testAtualizarUmBairroComIdCidadeNulo() {
		BairroDto bairro = new BairroDto("Jardim Novo", null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/bairro/1",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar já que o Id do Bairro é inválido")
	public void testAtualizarUmBairroComIdBairroInvalido() {
		BairroDto bairro = new BairroDto("Jardim Novo", 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers); 
		
		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/bairro/1000",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);

	}

	@Test
	@DisplayName("Deveria dar erro ao atualizar já que o Id da Cidade é inválido")
	public void testAtualizarUmBairroComIdCidadeInvalido() {
		BairroDto bairro = new BairroDto("Jardim Novo", 1000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BairroDto> request = new HttpEntity<>(bairro, headers);
		
		ResponseEntity<JsonNode> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/bairro/1",
				HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que o bairro foi excluido com sucesso")
	public void testDeletaUmBairroComIdValido() {
		ResponseEntity<BairroModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bairro/1", HttpMethod.DELETE, null, BairroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que o bairro não foi encontrado")
	public void testDeletaUmBairroComIdInvalido() {
		ResponseEntity<BairroModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/bairro/5", HttpMethod.DELETE, null, BairroModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
