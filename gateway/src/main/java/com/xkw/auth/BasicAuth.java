package com.xkw.auth;

import com.alibaba.fastjson.JSON;
import com.xkw.common.GatewayException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wangwei
 * @since 1.0
 */
@Component
public class BasicAuth {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RedisTemplate redisTemplate;

    public void doAuth(ServerHttpRequest request) {

        if (CollectionUtils.isEmpty(request.getHeaders().get(HttpHeaders.AUTHORIZATION)))
            throw new GatewayException("凭据无效，认证失败");
        String header = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        if (!header.startsWith("Basic ")) {
            throw new GatewayException("请使用Basic Authentication");
        }
        String authentication = new String((Base64Utils.decodeFromString(header.substring(6))));
        int index = authentication.indexOf(':');
        if (index < 0)
            throw new GatewayException("凭据无效，认证失败");

        String appId = authentication.substring(0, index);
        String secret = authentication.substring(index + 1);

        Application application = applicationService.getById(appId);
        if (application == null || !application.getSecret().equals(secret)) {
            throw new GatewayException("凭据无效，认证失败");
        }

        Permission neededPermission = new WildcardPermission(request.getPath().toString().replaceAll("^/+", "").replaceAll("/+", ":"));
        List<com.xkw.auth.Permission> permissions = permissionService.getByAppId(appId);

        if (!CollectionUtils.isEmpty(permissions)) {
            for (com.xkw.auth.Permission p : permissions) {
                Permission permission = new WildcardPermission(p.getPermission());
                if (permission.implies(neededPermission)) {
                    return;
                }
            }
        }

        throw new GatewayException("您无权访问此接口，请联系管理员！");
    }
}
