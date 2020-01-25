package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.group.domain.Group;

public interface ApplyService{

    BaseApply generateApply(BaseApplyRequestDto baseApplyRequestDto, Group group);
}
