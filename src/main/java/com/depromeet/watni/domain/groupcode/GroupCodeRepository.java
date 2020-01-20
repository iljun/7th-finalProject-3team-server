package com.depromeet.watni.domain.groupcode;

import com.depromeet.watni.domain.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupCodeRepository extends JpaRepository<GroupCode, Long> {
	Optional<GroupCode> findOneByCode(String code);
	Optional<GroupCode> findOneByGroupAndCode(Group group,String code);
}
