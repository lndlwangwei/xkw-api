package com.xkw.admin.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

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
    /**
     * 权限类型，0代表api访问权限，1代表数据权限
     */
    private int type;

    public Permission(String appId, String permission) {
        this.appId = appId;
        this.permission = permission;
    }

    public Permission() {
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
