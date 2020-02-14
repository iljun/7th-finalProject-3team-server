package com.depromeet.watni.domain.apply.repository;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.group.domain.Group;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeApplyRepository extends ApplyRepository<CodeApply>{
    Optional<BaseApply> findOneByGroup(Group group);
    Optional<BaseApply> findOneByCode(String code);
}
