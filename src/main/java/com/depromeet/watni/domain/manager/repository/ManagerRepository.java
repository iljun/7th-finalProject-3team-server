package com.depromeet.watni.domain.manager.repository;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findOneByGroupAndMemberId(Group group, Long memberId);

}
