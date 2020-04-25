package com.xkw.admin.controller;

import com.xkw.gateway.domain.ApiGroup;
import com.xkw.gateway.service.ApiGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/api-group")
public class ApiGroupController {

    @Autowired
    ApiGroupService apiGroupService;
    //    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public List<ApiGroup> getAll() {
        return apiGroupService.getAll();
    }

    @PostMapping
    public void save(@RequestBody @Valid ApiGroup apiGroup) {
        apiGroupService.save(apiGroup);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        apiGroupService.delete(id);
    }

    @GetMapping("/{id}/docs")
    public String getApiDocs(@PathVariable String id) {
        ApiGroup apiGroup = apiGroupService.getById(id);
        String url = String.format("%s%s", apiGroup.getUrl(), apiGroup.getApiInfoUrl());
        return restTemplate.getForObject(url, String.class);
    }
}
