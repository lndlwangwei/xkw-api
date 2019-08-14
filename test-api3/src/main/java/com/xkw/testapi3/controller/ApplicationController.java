package com.xkw.testapi3.controller;

import com.xkw.testapi3.domain.Application;
import com.xkw.testapi3.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
