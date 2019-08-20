package com.xkw;

import com.xkw.gateway.service.ApiGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {

    @Autowired
    ApiGroupService apiGroupService;

    @Test
    public void contextLoads() {
        //        ApiGroup application = applicationService.getCache("zujuan");
        //        System.out.println(application);
    }

}
