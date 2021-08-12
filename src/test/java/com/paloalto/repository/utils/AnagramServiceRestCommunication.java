package com.paloalto.repository.utils;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;

public class AnagramServiceRestCommunication {

    private final HttpHeaders headers;
    private final TestRestTemplate testRestTemplate;

    public AnagramServiceRestCommunication(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
        headers = new HttpHeaders();
        resetHeaders();
    }

    public void resetHeaders() {
        headers.clear();
        headers.put("Content-Type", singletonList("application/json"));
    }

    public ResponseEntity<String> sendGetRequest(final String url) {
        return this.sendGetRequest(url, this.headers);
    }

    public ResponseEntity<String> sendGetRequest(final String url, HttpHeaders headers) {
        HttpMethod method = GET;
        return testRestTemplate.exchange(url, method, new HttpEntity(headers), String.class);
    }
}
