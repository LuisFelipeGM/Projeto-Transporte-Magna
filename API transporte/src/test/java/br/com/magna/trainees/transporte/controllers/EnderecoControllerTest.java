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

import br.com.magna.trainees.transporte.dtos.BairroDto;
import br.com.magna.trainees.transporte.dtos.EnderecoDto;
import br.com.magna.trainees.transporte.dtos.EstacaoDto;
import br.com.magna.trainees.transporte.models.BairroModel;
import br.com.magna.trainees.transporte.models.EnderecoModel;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.repositories.BairroRepository;
import br.com.magna.trainees.transporte.repositories.EnderecoRepository;
import br.com.magna.trainees.transporte.repositories.EstacaoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EnderecoControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BairroRepository bairroRepository;

	@Autowired
	private EstacaoRepository estacaoRepository;

	@BeforeEach
	public void inicializar() {
		criarBairro();
		criarEstacao();
	}

	@AfterEach
	public void finalizar() {
		enderecoRepository.LimparDadosERedefinirSequence();
		bairroRepository.LimparDadosERedefinirSequence();
		estacaoRepository.LimparDadosERedefinirSequence();

	}

	public void criarBairro() {
		BairroDto bairro = new BairroDto("Jardim Helena", 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/bairro/", bairro, BairroModel.class);
	}

	public void criarEstacao() {
		EstacaoDto estacao = new EstacaoDto("Pinheiros", null, null);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/estacao/", estacao, EstacaoModel.class);
	}

	public void criarEndereco() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", 1697, null, 1l, 1l);
		restTemplate.postForEntity("http://localhost:" + randomServerPort + "/endereco/", endereco,
				EnderecoModel.class);
	}

	// TESTES POST

	@Test
	@DisplayName("Deveria cadastrar um Endereço já que as informações estão validas")
	public void testCadastrarUmEnderecoComInformacoesValidas() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", 1697, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<EnderecoModel> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, EnderecoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());

	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Endereco já que seu logradouro não pode ser nulo")
	public void testCadastrarUmEnderecoComLogradouroNulo() {
		EnderecoDto endereco = new EnderecoDto(null, 1234, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("LOGRADOURO : O Logradouro é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Endereco já que seu numero não pode ser nulo")
	public void testCadastrarUmEnderecoComNumeroNulo() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", null, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("NUMERO : O Numero é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Endereco já que o IdEstacao não pode ser nulo")
	public void testCadastrarUmEnderecoComIdEstacaoNulo() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", 1927, null, null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDESTACAO : O Id da Estação é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Endereco já que o IdBairro não pode ser nulo")
	public void testCadastrarUmEnderecoComIdBBairroNulo() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", 1927, null, 1l, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);

		String responseBody = response.getBody();
		Assert.assertNotNull(responseBody);
		Assert.assertTrue(responseBody.contains("IDBAIRRO : O Id do Bairro é obrigatório"));
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Endereco já que o Id da Estacao é invalido")
	public void testCadastrarUmEnderecoComIdEstacaoInvalido() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", 1927, null, 1000l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar erro ao cadastrar um Endereco já que o Id da Bairro é invalido")
	public void testCadastrarUmEnderecoComIdBairroInvalido() {
		EnderecoDto endereco = new EnderecoDto("Avenida Brasil", 1927, null, 1l, 1000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(endereco, headers);

		ResponseEntity<String> response = restTemplate
				.postForEntity("http://localhost:" + randomServerPort + "/endereco/", request, String.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);

		Assert.assertNotNull(response.getBody());
	}

	// TESTES GET

	@Test
	@DisplayName("Deveria listar todos os endereços")
	public void testListarTodosOsEnderecosDoSistema() {
		criarEndereco();
		ResponseEntity<List<EnderecoModel>> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<EnderecoModel>>() {
				});

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria listar o endereço de id valido")
	public void testListarUmEnderecoComIdValido() {
		criarEndereco();
		ResponseEntity<EnderecoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/endereco/1", EnderecoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.OK, statusCode);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Deveria dar not found pois não achou o endereco de id invalido")
	public void testListarUmEnderecoComIdInvalido() {
		criarEndereco();
		ResponseEntity<EnderecoModel> response = restTemplate
				.getForEntity("http://localhost:" + randomServerPort + "/endereco/371", EnderecoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}

	// TESTES PUT

	@Test
	@DisplayName("Deveria retortar o endereco de ID 1 alterado com sucesso")
	public void testAtualizarUmEnderecoComIdValido() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", 124, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<EnderecoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, EnderecoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.CREATED, statusCode);
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(enderecoAtualizado.logradouro(), response.getBody().getLogradouro());
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que seu logradouro não pode ser nulo")
	public void testAtualizarUmEnderecoComNomeNulo() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto(null, 124, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que seu numero não pode ser nulo")
	public void testAtualizarUmEnderecoComNumeroNulo() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", null, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que o Id da Estação não pode ser nulo")
	public void testAtualizarUmEnderecoComIdEstacaoNulo() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", 124, null, null, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que o Id do Bairro não pode ser nulo")
	public void testAtualizarUmEnderecoComIdBairroNulo() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", 124, null, 1l, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que o Id da Estacao é invalido")
	public void testAtualizarUmEnderecoComIdEstacaoInvalido() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", 124, null, 1000l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que o Id do Bairro é invalido")
	public void testAtualizarUmEnderecoComIdBairroInvalido() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", 124, null, 1l, 1000l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
	}
	
	@Test
	@DisplayName("Deveria dar erro ao atualizar um Endereco já que o Id do Endereco é invalido")
	public void testAtualizarUmEnderecoComIdEnderecoInvalido() {
		criarEndereco();
		EnderecoDto enderecoAtualizado = new EnderecoDto("Rua Itupeva", 124, null, 1l, 1l);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EnderecoDto> request = new HttpEntity<>(enderecoAtualizado, headers);

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/100", HttpMethod.PUT, request, JsonNode.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
	}
	
	
	// TESTES DELETE
	
	@Test
	@DisplayName("Deveria retornar no Content já que o endereço foi excluido com sucesso")
	public void testDeletaUmEnderecoComIdValido() {
		criarEndereco();
		ResponseEntity<EnderecoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/1", HttpMethod.DELETE, null, EnderecoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NO_CONTENT, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	@Test
	@DisplayName("Deveria retornar not found já que o endereço não foi encontrado")
	public void testDeletaUmEnderecoComIdInvalido() {
		criarEndereco();
		ResponseEntity<EnderecoModel> response = restTemplate.exchange(
				"http://localhost:" + randomServerPort + "/endereco/100", HttpMethod.DELETE, null, EnderecoModel.class);

		HttpStatusCode statusCode = response.getStatusCode();
		Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Assert.assertNull(response.getBody());
	}
	
	

}
