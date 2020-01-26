package com.depromeet.watni.domain.apply.repository;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeApplyRepository extends ApplyRepository<CodeApply>{
    BaseApply findOneByGroup(Group group);
}
