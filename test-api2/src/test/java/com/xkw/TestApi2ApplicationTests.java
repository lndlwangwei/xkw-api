package com.xkw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApi2ApplicationTests {

    @Test
    public void contextLoads() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i <= 4; i++) {

            new Thread(() -> {
                for (int j = 0; j < 3000; j++) {
                    long start = System.currentTimeMillis();
                    String res =
                        restTemplate.getForObject("http://localhost:8071/qbm/abc", String.class);
                    long end = System.currentTimeMillis();
                    System.out.println(String.format("qbm %s用时:%s", j, (end - start)));
                }

            }).start();

        }

        for (int i = 0; i <= 4; i++) {

            new Thread(() -> {
                for (int j = 0; j < 3000; j++) {
                    long start = System.currentTimeMillis();
                    String res =
                        restTemplate.getForObject("http://localhost:8071/mdm/abc", String.class);
                    long end = System.currentTimeMillis();
                    System.out.println(String.format("mdm %s用时:%s", j, (end - start)));
                }

            }).start();

        }

        Thread.sleep(100000);
    }

}
