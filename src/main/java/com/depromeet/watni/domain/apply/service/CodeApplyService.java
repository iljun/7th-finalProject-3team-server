package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.repository.ApplyRepository;
import org.springframework.stereotype.Service;

@Service
public class CodeApplyService implements ApplyService{

    private final ApplyRepository applyRepository;
    public CodeApplyService(ApplyRepository applyRepository) {
        this.applyRepository = applyRepository;
    }

    @Override
    public BaseApply generateApply(BaseApplyRequestDto baseApplyRequestDto) {
        // generate Code
        return null;
    }
}
