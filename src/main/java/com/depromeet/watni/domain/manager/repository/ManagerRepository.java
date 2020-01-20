package com.depromeet.watni.domain.manager.repository;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.manager.domain.Manager;
import com.depromeet.watni.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findOneByGroupAndMember(Group group, Member member);

}
