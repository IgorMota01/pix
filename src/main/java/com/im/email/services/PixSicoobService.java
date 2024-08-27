package com.im.email.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PixSicoobService {

    @Value("${sicoob.api.url}")
    private String sicoobApiUrl;

    @Value("${sicoob.api.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    public PixSicoobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getPixCobranca(String txid, String accessToken) {
        String url = sicoobApiUrl + "/pix/api/v2/cob/" + txid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("client_id", clientId);
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
