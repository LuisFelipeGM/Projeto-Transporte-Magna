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

import br.com.magna.trainees.transporte.dtos.ConexaoDto;
import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.dtos.LinhaDto;
import br.com.magna.trainees.transporte.models.ConexaoModel;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.models.LinhaModel;
import br.com.magna.trainees.transporte.repositories.ConexaoRepository;
import br.com.magna.trainees.transporte.repositories.EstacaoRepository;
import br.com.magna.trainees.transporte.repositories.LinhaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ConexaoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private LinhaRepository linhaRepository;

	@Autowired
	private EstacaoRepository estacaoRepository;

	@Autowired
	private ConexaoRepository conexaoRepository;

	@BeforeEach
	public void inicializar() {
		criarLinha();
		criarEstacao();
		criarConexao();
	}

	@AfterEach
	public void finalizar() {
		conexaoRepository.LimparDadosERedefinirSequence();
		estacaoRepository.LimparDadosERedefinirSequence();
		linhaRepository.LimparDadosERedefinirSequence();
	}

	public void criarLinha() {
		LinhaDto linha = new LinhaDto("Amarela", 5);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/linha/", linha, LinhaModel.class);

	}

	public void criarEstacao() {
		EstacaoDto estacao = new EstacaoDto("Pinheiros", null, null);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/estacao/", estacao, EstacaoModel.class);
	}

	public void criarConexao() {
		ConexaoDto conexao = new ConexaoDto(1l, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/conexao/", conexao, ConexaoModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar uma Conexão já que suas informações estão validas")
	public void testCadastrarUmaConexaoComInformacoesValidas() {
		ConexaoDto conexao = new ConexaoDto(1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConexaoDto> request = new HttpEntity<>(conexao, headers);

		ResponseEntity<ConexaoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/conexao/", request, ConexaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Conexão já que o id da linha não pode ser nulo")
	public void testCadastrarUmaConexaoComIdLinhaNulo() {
		ConexaoDto conexao = new ConexaoDto(null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConexaoDto> request = new HttpEntity<>(conexao, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/conexao/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDLINHA : O Id da Linha é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Conexão já que o id da estação não pode ser nulo")
	public void testCadastrarUmaConexaoComIdEstacaoNulo() {
		ConexaoDto conexao = new ConexaoDto(1l, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConexaoDto> request = new HttpEntity<>(conexao, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/conexao/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDESTACAO : O Id da Estação é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Conexão já que o id da Linha é invalida")
	public void testCadastrarUmaConexaoComIdLinhaInvalida() {
		ConexaoDto conexao = new ConexaoDto(10000l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConexaoDto> request = new HttpEntity<>(conexao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/conexao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar uma Conexão já que o id da Estação é invalida")
	public void testCadastrarUmaConexaoComIdEstacaoInvalida() {
		ConexaoDto conexao = new ConexaoDto(1l, 10000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ConexaoDto> request = new HttpEntity<>(conexao, headers);

		ResponseEntity<JsonNode> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/conexao/", request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		JsonNode responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
	}

	// TESTE GET

	@Test
	@DisplayName("Deveria listar todas as conexões")
	public void testListarTodasAsConexoesDoSistema() {
		ResponseEntity<List<ConexaoModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/conexao/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ConexaoModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria listar a conexao de id valido")
	public void testListarUmaLinhaComIdValido() {
		ResponseEntity<ConexaoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/conexao/1", ConexaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou a conexao de id invalido")
	public void testListarUmaLinhaComIdInvalido() {
		ResponseEntity<ConexaoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/conexao/498", ConexaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES DELETE

	@Test
	@DisplayName("Deveria retornar no Content já que a conexão foi excluida com sucesso")
	public void testDeletaUmaLinhaComIdValido() {
		ResponseEntity<ConexaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/conexao/1", HttpMethod.DELETE, null, ConexaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria retornar not found já que a conexão não foi encontrada")
	public void testDeletaUmaLinhaComIdInvalido() {
		ResponseEntity<ConexaoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/conexao/100", HttpMethod.DELETE, null, ConexaoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

}
