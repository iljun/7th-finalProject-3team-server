package com.depromeet.watni.domain.manager.service;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.manager.domain.Manager;
import com.depromeet.watni.domain.manager.repository.ManagerRepository;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager registerManager (Group group, Member member) {
        boolean isExistManager = group.getManagers()
                .stream()
                .filter(m -> m.getMember().getMemberId() == member.getMemberId())
                .findAny()
                .isPresent();

        if (isExistManager) {
            throw new BadRequestException("ALREADY EXISTS MANAGER");
        }

        Manager manager = Manager
                .builder()
                .group(group)
                .member(member)
                .build();

        return managerRepository.save(manager);
    }

    public void deleteManager (Group group, Member member) {
        Manager manager = managerRepository.findOneByGroupAndMember(group, member)
                .orElseThrow(() -> new NotFoundException("NOT FOUND MANAGER"));
        manager.isDeleted();
        managerRepository.save(manager);
    }
}
