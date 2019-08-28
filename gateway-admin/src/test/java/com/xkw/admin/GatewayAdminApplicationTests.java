package com.xkw.admin;

import com.xkw.gateway.domain.Permission;
import com.xkw.gateway.service.ApiGroupService;
import com.xkw.gateway.service.ApplicationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayAdminApplicationTests {

    private List<String> apis = Arrays.asList("qbm/v1/banks", "master/v1/stages");

    @Autowired
    ApiGroupService apiGroupService;
    @Autowired
    ApplicationService applicationService;

    @Test
    public void contextLoads() throws InterruptedException {

        for (int j = 0; j < 1; j++) {
            new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    try {
                        //                        Thread.sleep(1000);

                        int randomApiIndex = new Random().nextInt(apis.size());
                        //                int randomApiIndex = 0;
                        String api = apis.get(randomApiIndex);
                        requestApi(api, i);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }


        Thread.sleep(5000000);
    }

    int count;

    public synchronized int getCount() {
        System.out.println(count);
        return count++;
    }

    public void requestApi(String api, int i) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        doGatewayAuth(headers);

        if (api.startsWith("master")) {
            doMdmAuth(headers);
        }

        HttpEntity httpEntity = new HttpEntity(null, headers);

        //        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8093/master/v1/stages", HttpMethod.GET, httpEntity, String.class);

        ResponseEntity<String> res = null;
        try {
            res =
                restTemplate.exchange(String.format("http://localhost:8093/%s", api), HttpMethod.GET, httpEntity, String.class);

            System.out.println(String.format("api %s: %s, response: %s", i, api, res.getBody()));
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }
    }

    @Test
    public void getCache() {
        //        ApiGroup application = applicationService.getCache("zujuan");
        //        System.out.println(application);

        List<Permission> permissions =
            applicationService.getAppPermission("zujuan", Permission.TYPE_API);
        System.out.println(permissions);
        for (Permission permission : permissions) {
            permission.getAppId();
            System.out.println();
            System.out.println();
        }
    }

    private void doMdmAuth(HttpHeaders headers) {
        String token =
            new String(Base64.getEncoder().encode("ali:0cx-yx#xn8RArA162JLbG)4nJHOth5Ut".getBytes()));

        headers.add("Authorization", String.format("Basic %s", token));
    }

    private void doGatewayAuth(HttpHeaders headers) {
        String appId = "ali";
        String secret = "0cx-yx#xn8RArA162JLbG)4nJHOth5Ut";

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("timestamp", System.currentTimeMillis());
        String signature =
            Jwts.builder().setClaims(new DefaultClaims(tokenParams)).signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

        headers.add("appid", appId);
        headers.add("signature", signature);
    }

    public static void main(String[] args) {
        String appId = "ali";
        String secret = "0cx-yx#xn8RArA162JLbG)4nJHOth5Ut";

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("timestamp", System.currentTimeMillis());
        String signature =
            Jwts.builder().setClaims(new DefaultClaims(tokenParams)).signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

        System.out.println(signature);
    }
}
