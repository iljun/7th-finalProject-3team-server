package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.group.domain.Group;

public interface ApplyService{

    BaseApply generateApply(String content, Group group);
    BaseApply getApply(String content, Group group);
    void checkApply(String content, Group group);
}
