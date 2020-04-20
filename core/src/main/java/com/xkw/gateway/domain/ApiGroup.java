package com.xkw.gateway.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangwei
 * @since 1.0
 */
@Entity
public class ApiGroup implements Serializable {

    private static final long serialVersionUID = -4241211318224748006L;
    @Id
    @NotNull(message = "id不能为空")
    @Size(min = 1)
    private String id;
    @NotNull(message = "name不能为空")
    @Size(min = 1)
    private String name;
    @NotNull(message = "url不能为空")
    @Size(min = 1)
    private String url;
    private String apiPrefix;
    // api详情的url地址，相对于apiPrefix
    private String apiInfoUrl;
    @NotNull(message = "description不能为空")
    @Size(min = 1)
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiPrefix() {
        return apiPrefix;
    }

    public void setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getApiInfoUrl() {
        return apiInfoUrl;
    }

    public void setApiInfoUrl(String apiInfoUrl) {
        this.apiInfoUrl = apiInfoUrl;
    }
}
