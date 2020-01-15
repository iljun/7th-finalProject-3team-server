package com.depromeet.watni.domain.manager.api;

import com.depromeet.watni.domain.manager.service.ManagerService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerApi {
    private final ManagerService managerService;
    public ManagerApi(ManagerService managerService) {
        this.managerService = managerService;
    }
}
