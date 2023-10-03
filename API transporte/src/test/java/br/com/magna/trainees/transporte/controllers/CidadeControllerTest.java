package br.com.magna.trainees.transporte.controllers;

import br.com.magna.trainees.transporte.models.CidadeModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CidadeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomServerPort;

    @Test
    @DisplayName("Deveria listar todas as cidades")
    public void testListarTodasAsCidades(){
        ResponseEntity<List<CidadeModel>> response = restTemplate.exchange(
                "http://localhost:" + randomServerPort + "/cidade/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CidadeModel>>() {}
        );

        Assert.assertEquals(200, response.getStatusCode().value());
        List<CidadeModel> cidades = response.getBody();
        Assert.assertNotNull(cidades);
        Assert.assertTrue(cidades.size() > 0);

    }

    @Test
    @DisplayName("Deveria listar a cidade de id 1")
    public void testListarUmaCidadeComIdValido() {
        ResponseEntity<CidadeModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/cidade/1", CidadeModel.class);

        Assert.assertEquals(200, response.getStatusCode().value());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Deveria dar not found pois não achou a cidade de id 99")
    public void testListarUmaCidadeSemIdNoBanco() {
        ResponseEntity<CidadeModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/cidade/99", CidadeModel.class);

        Assert.assertEquals(404, response.getStatusCode().value());
        Assert.assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deveria encontrar uma cidade a partir de seu nome")
    public void testListarCidadeAPartirDoSeuNome(){
        ResponseEntity<CidadeModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/cidade/nome/Barueri", CidadeModel.class);

        Assert.assertEquals(200, response.getStatusCode().value());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals("Barueri", response.getBody().getNome());
    }

    @Test
    @DisplayName("Deveria dar not found pois não achou a cidade com nome invalido")
    public void testListarCidadeComNomeInvalido(){
        ResponseEntity<CidadeModel> response = restTemplate.getForEntity(
                "http://localhost:" + randomServerPort + "/cidade/nome/Salvador", CidadeModel.class);

        Assert.assertEquals(404, response.getStatusCode().value());
        Assert.assertNull(response.getBody());
    }

}
