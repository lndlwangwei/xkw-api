package com.xkw.testapi3;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApi3ApplicationTests {

    @Test
    public void contextLoads() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        //        doMdmAuth(headers);
        doQbmAuth(headers);

        HttpEntity httpEntity = new HttpEntity(null, headers);

        //        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8093/master/v1/stages", HttpMethod.GET, httpEntity, String.class);

        ResponseEntity<String> res = null;
        try {
            res =
                restTemplate.exchange("http://localhost:8093/qbm/v1/banks", HttpMethod.GET, httpEntity, String.class);

            System.out.println(res.getBody());
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }

    }

    private void doMdmAuth(HttpHeaders headers) {
        String token =
            new String(Base64.getEncoder().encode("qbm:JyBGJLng_TMyCwwj2WY4B?Klvj8#!i89".getBytes()));

        headers.add("Authorization", String.format("Basic %s", token));
    }

    private void doQbmAuth(HttpHeaders headers) {
        String appId = "zujuan";
        String secret = "87aJG8L6WiJo9zqL&FM2unYIW&DyE4u7";

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("timestamp", System.currentTimeMillis());
        String signature =
            Jwts.builder().setClaims(new DefaultClaims(tokenParams)).signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

        headers.add("appid", appId);
        headers.add("signature", signature);

        String basicAuthToken =
            new String(Base64.getEncoder().encode("zujuan:zujuan-password".getBytes()));
        headers.add("Authorization", String.format("Basic %s", basicAuthToken));

    }
}
