package br.com.magna.trainees.transporte.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
import br.com.magna.trainees.transporte.models.EstadoModel;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EstadoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomServerPort;

    @Test
    @DisplayName("Deveria listar todos os estados")
    public void testListarTodosOsEstados(){
        ResponseEntity<List<EstadoModel>> response = restTemplate.exchange(
                "http://localhost:" + randomServerPort + "/estado/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EstadoModel>>() {}
        );

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.OK, statusCode);

        List<EstadoModel> estados = response.getBody();
        Assert.assertNotNull(estados);
        Assert.assertTrue(estados.size() > 0);
    }



    @Test
    @DisplayName("Deveria listar o estado de id 1")
    public void testListarUmEstado(){
        ResponseEntity<EstadoModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/estado/1", EstadoModel.class);

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.OK, statusCode);

        Assert.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Deveria dar not found pois não achou o estado com id 99")
    public void testListarEstadoSemIdNoBanco(){
        ResponseEntity<EstadoModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/estado/99", EstadoModel.class);

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);

        Assert.assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deveria encontrar um estado a partir de seu nome")
    public void testListarEstadoAPartirDoSeuNome(){
        ResponseEntity<EstadoModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/estado/nome/Amazonas", EstadoModel.class);

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.OK, statusCode);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("Amazonas", response.getBody().getNome());
    }

    @Test
    @DisplayName("Deveria dar not found pois não achou o estado com nome invalido")
    public void testListarEstadoSemRegistroNoBanco(){
        ResponseEntity<EstadoModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/estado/nome/Alabama", EstadoModel.class);

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
        Assert.assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deveria encontrar um estado a partir de sua sigla")
    public void TestListarEstadoAPartirDeSuaSigla(){
        ResponseEntity<EstadoModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/estado/sigla/SP", EstadoModel.class);

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.OK, statusCode);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("São Paulo", response.getBody().getNome());
    }

    @Test
    @DisplayName("Deveria dar not found pois não achou o estado com sigla invalida")
    public void TestListarEstadoComSiglaInvalida(){
        ResponseEntity<EstadoModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/estado/sigla/RP", EstadoModel.class);

        HttpStatusCode statusCode =  response.getStatusCode();
        Assert.assertEquals(HttpStatus.NOT_FOUND, statusCode);
        Assert.assertNull(response.getBody());
    }




}
