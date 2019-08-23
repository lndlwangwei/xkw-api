package com.xkw.admin.controller;

import com.xkw.gateway.domain.Application;
import com.xkw.gateway.domain.Permission;
import com.xkw.gateway.service.ApplicationService;
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

    @DeleteMapping("/{appId}")
    public void delete(@PathVariable String appId) {
        applicationService.delete(appId);
    }

    @GetMapping("/{appId}/permissions")
    public List<Permission> getAppPermission(@PathVariable String appId) {
        return applicationService.getAppPermission(appId, Permission.TYPE_API);
    }

    @PutMapping("/{appId}/permissions")
    public void updateAppPermission(@PathVariable String appId, @RequestParam String groupId, @RequestBody List<String> permissionsStr) {
        applicationService.updateAppPermission(appId, groupId, permissionsStr);
    }
}
