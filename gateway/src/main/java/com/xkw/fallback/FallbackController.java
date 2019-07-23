package com.xkw.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * @author wangwei
 * @since 1.0
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping
    public String fallback() {
        return "this is fallback messages!!!";
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[\\(（][ 共\\d分每空词字;、，,+ 本小题满大第句]{0,20}\\d{1,2}分[\\)）]");

        System.out.println(pattern.matcher("（满分2分）").matches());
    }
}
