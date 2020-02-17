package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;

public interface ApplyService{

    BaseApply generateApply(BaseApplyRequestDto baseApplyRequestDto, Group group);
    BaseApply getApply(BaseApplyRequestDto baseApplyRequestDto, Group group);
    void checkApply(BaseApplyRequestDto baseApplyRequestDto, Group group);
    GroupResponseDto confirmApply(BaseApplyRequestDto baseApplyRequestDto);
}
