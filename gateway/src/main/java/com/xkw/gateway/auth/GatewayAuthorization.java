package com.xkw.gateway.auth;

import com.xkw.gateway.common.GatewayException;
import com.xkw.gateway.domain.Permission;
import com.xkw.gateway.service.ApiGroupService;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@Component
public class GatewayAuthorization {

    @Autowired
    ApiGroupService apiGroupService;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public void doAuthorization(String appId, ServerHttpRequest request) {
        List<Permission> permissions = apiGroupService.getAppPermission(appId, Permission.TYPE_API);
        if (CollectionUtils.isEmpty(permissions)) {
            throw new GatewayException("您无权访问此接口！");
        }

        String path = request.getPath().value();
        String method = request.getMethod().name();
        for (Permission permission : permissions) {
            if (StringUtils.isEmpty(permission.getApiPermissionPath())
                || CollectionUtils.isEmpty(permission.getApiPermissionMethod())) {
                continue;
            }

            boolean match = this.pathMatcher.match(permission.getApiPermissionPath(), path);
            if (match && (permission.getApiPermissionMethod().contains(method)
                || permission.getApiPermissionMethod().contains("*"))) {
                // path 和 method都验证通过，此处才放行
                return;
            }
        }

        throw new GatewayException("您无权访问此接口！");
    }

    public static void main(String[] args) {
        org.springframework.util.AntPathMatcher pathMatcher =
            new org.springframework.util.AntPathMatcher();
        System.out.println(pathMatcher.match("/a/{asdfa}/c:GET", "/a/b/c"));
    }
}
