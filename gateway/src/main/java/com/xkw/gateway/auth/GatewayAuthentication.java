package com.xkw.gateway.auth;

import com.xkw.gateway.common.GatewayException;
import com.xkw.gateway.domain.ApiGroup;
import com.xkw.gateway.service.ApiGroupService;
import com.xkw.gateway.service.PermissionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@Component
public class GatewayAuthentication {

    @Autowired
    ApiGroupService apiGroupService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RedisTemplate redisTemplate;

    public String doJwtAuthc(ServerHttpRequest request) {
        String appId = request.getHeaders().getFirst("appid");
        String signature = request.getHeaders().getFirst("signature");
        validateSignature(appId, signature);

        return appId;
    }

    public void doBasicAuthc(ServerHttpRequest request) {

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

        ApiGroup apiGroup = apiGroupService.getById(appId);
        // todo 密码加密处理
        if (apiGroup == null || !apiGroup.getSecret().equals(secret)) {
            throw new GatewayException("凭据无效，认证失败");
        }

        Permission neededPermission = new WildcardPermission(request.getPath().toString().replaceAll("^/+", "").replaceAll("/+", ":"));
        List<com.xkw.gateway.domain.Permission> permissions = permissionService.getByAppId(appId);

        if (!CollectionUtils.isEmpty(permissions)) {
            for (com.xkw.gateway.domain.Permission p : permissions) {
                Permission permission = new WildcardPermission(p.getPermission());
                if (permission.implies(neededPermission)) {
                    return;
                }
            }
        }

        throw new GatewayException("您无权访问此接口，请联系管理员！");
    }

    private void validateSignature(String appId, String signature) {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(signature)) {
            throw new GatewayException("签名信息不完整");
        }
        ApiGroup apiGroup = apiGroupService.getById(appId);
        String secret = apiGroup.getSecret();
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(signature);
        } catch (Exception e) {
            throw new GatewayException("签名信息不正确");
        }
        Claims body = claimsJws.getBody();
        Date timestamp = body.get("timestamp", Date.class);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -5);
        if (timestamp.before(c.getTime())) {
            throw new GatewayException("签名信息已过期");
        }
    }
}
