package com.xkw.gateway.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@Entity
public class Permission implements Serializable {

    private static final long serialVersionUID = 5317313392933206185L;

    public static final int TYPE_API = 0;
    public static final int TYPE_DATA = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String appId;

    private String groupId;

    private String permission;
    /**
     * 权限类型，0代表api访问权限，1代表数据权限
     */
    private int type;

    @Transient
    private String apiPermissionPath;
    @Transient
    private List<String> apiPermissionMethod;

    public Permission(String appId, String groupId, String permission) {
        this.appId = appId;
        this.groupId = groupId;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getApiPermissionPath() {
        return apiPermissionPath;
    }

    public void setApiPermissionPath(String apiPermissionPath) {
        this.apiPermissionPath = apiPermissionPath;
    }

    public List<String> getApiPermissionMethod() {
        return apiPermissionMethod;
    }

    public void setApiPermissionMethod(List<String> apiPermissionMethod) {
        this.apiPermissionMethod = apiPermissionMethod;
    }
}
