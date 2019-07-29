package com.xkw.auth;

import org.apache.shiro.codec.Base64;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author wangwei
 * @since 1.0
 */
@Entity
public class Permission implements Serializable {

    @Id
    private int id;

    private String appId;

    private String permission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public static void main(String[] args) {
        System.out.println(new String(Base64.encode("qbm:qbm-password".getBytes(StandardCharsets.UTF_8))));
    }
}
