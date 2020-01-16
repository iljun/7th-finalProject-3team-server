package com.depromeet.watni.domain.manager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depromeet.watni.domain.group.Group;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
	Optional<Manager> findOneByGroupAndMemberId(Group group,Long memberId);
}
