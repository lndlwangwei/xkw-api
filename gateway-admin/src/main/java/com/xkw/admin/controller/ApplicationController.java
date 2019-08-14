package com.xkw.admin.controller;

import com.xkw.admin.domain.Application;
import com.xkw.admin.domain.Permission;
import com.xkw.admin.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangwei
 * @since 1.0
 */
@RestController
@CrossOrigin
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping
    public List<Application> getAll() {
        return applicationService.getAll();
    }

    @PostMapping
    public void save(@RequestBody Application application) {
        applicationService.save(application);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        applicationService.delete(id);
    }

    @GetMapping("/{appId}/permissions")
    public List<Permission> getAppPermission(@PathVariable String appId) {
        return applicationService.getAppPermission(appId);
    }

    @PutMapping("/{appId}/permissions")
    public void updateAppPermission(@PathVariable String appId, @RequestBody List<String> permissionsStr) {
        applicationService.updateAppPermission(appId, permissionsStr);
    }
}
