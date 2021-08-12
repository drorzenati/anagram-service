package com.paloalto.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITTestIntegrationParent {

    public static final String URL_PREFIX = "http://localhost:";
    @LocalServerPort
    int randomServerPort;

    protected <T> ResponseEntity<T> sendGetRequest(String url, Class<T> responseType) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = URL_PREFIX + randomServerPort + url;
        URI uri = new URI(baseUrl);
        return restTemplate.getForEntity(uri, responseType);
    }
}
