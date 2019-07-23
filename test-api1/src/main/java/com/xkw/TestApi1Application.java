package com.xkw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SpringBootApplication
@RestController
public class TestApi1Application {

    public static void main(String[] args) {
        SpringApplication.run(TestApi1Application.class, args);
    }

    @RequestMapping
    public String test(HttpServletRequest request) {
        System.out.println(request.getRequestURL());
        return "this is api 1";
    }

    @RequestMapping("/test-filters")
    public String testFilters(Map<String, String> params, @RequestHeader HttpHeaders header) {
        System.out.println(params);
        System.out.println(header);
        return params.toString();
    }

    @RequestMapping("/addedPath/**")
    public String testPrefixPath() {
        return "addedPath";
    }

    @RequestMapping("bar")
    public String testRewritePath() {
        return "bar";
    }
}
