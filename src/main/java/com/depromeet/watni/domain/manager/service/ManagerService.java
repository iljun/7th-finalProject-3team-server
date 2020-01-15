package com.depromeet.watni.domain.manager.service;

import com.depromeet.watni.domain.manager.repository.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

}
