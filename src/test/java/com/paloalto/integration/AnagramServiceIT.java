package com.paloalto.integration;

import com.paloalto.model.Anagram;
import com.paloalto.model.Statistic;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AnagramServiceIT extends ITTestIntegrationParent {

    public static final int TOTAL_REQUESTS = 10;
    public static final int TOTAL_WORDS = 351075;
    public static Anagram EXPECTED_ANAGRAM1 = Anagram.builder().similar(new HashSet<>(Arrays.asList("appel", "pepla"))).build();
    public static Anagram EXPECTED_ANAGRAM2 = Anagram.builder().similar(new HashSet<>(Arrays.asList("apple", "pepla"))).build();
    public static Anagram EXPECTED_ANAGRAM3 = Anagram.builder().similar(new HashSet<>(Arrays.asList("ates", "east", "etas", "sate", "seat", "seta", "teas"))).build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAllAnagrams() throws URISyntaxException {
        ResponseEntity<Anagram> result = sendGetRequest("/api/v1/similar?word=apple", Anagram.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(EXPECTED_ANAGRAM1);

        result = sendGetRequest("/api/v1/similar?word=appel", Anagram.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(EXPECTED_ANAGRAM2);

        result = sendGetRequest("/api/v1/similar?word=eats", Anagram.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(EXPECTED_ANAGRAM3);
    }

    @Test
    public void getAllAnagramsGivenNullWordThenBadRequestExceptionExpected() throws URISyntaxException {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("Required request parameter 'word' for method parameter type String is not present");
        sendGetRequest("/api/v1/similar", Anagram.class);
    }

    @Test
    public void getAllAnagramsGivenEmptyWordThenEmptyResultExpected() throws URISyntaxException {
        ResponseEntity<Anagram> result = sendGetRequest("/api/v1/similar?word", Anagram.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(new Anagram());

        result = sendGetRequest("/api/v1/similar?word=", Anagram.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(new Anagram());
    }

    @Test
    public void getGetStatistics() throws URISyntaxException {
        for (int i = 0; i < TOTAL_REQUESTS; i++) {
            sendGetRequest("/api/v1/similar?word=apple", Anagram.class);
        }
        ResponseEntity<Statistic> result = sendGetRequest("/api/v1/stats", Statistic.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getTotalRequests()).isEqualTo(TOTAL_REQUESTS);
        assertThat(result.getBody().getTotalWords()).isEqualTo(TOTAL_WORDS);
        assertThat(result.getBody().getAvgProcessingTimeNs()).isPositive();
    }
}
