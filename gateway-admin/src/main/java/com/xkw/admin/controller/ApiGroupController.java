package com.xkw.admin.controller;

import com.xkw.gateway.domain.ApiGroup;
import com.xkw.gateway.service.ApiGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<ApiGroup> getAll() {
        return apiGroupService.getAll();
    }

    @PostMapping
    public void save(@RequestBody @Valid ApiGroup application) {
        apiGroupService.save(application);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        apiGroupService.delete(id);
    }


}
