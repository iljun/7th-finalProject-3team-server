package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.repository.ApplyRepository;
import com.depromeet.watni.domain.apply.repository.CodeApplyRepository;
import com.depromeet.watni.domain.group.domain.Group;
import org.springframework.stereotype.Service;

@Service
public class CodeApplyService implements ApplyService{

    private final ApplyRepository applyRepository;
    private final CodeApplyRepository codeApplyRepository;
    public CodeApplyService(ApplyRepository applyRepository, CodeApplyRepository codeApplyRepository) {
        this.applyRepository = applyRepository;
        this.codeApplyRepository = codeApplyRepository;
    }

    @Override
    public BaseApply generateApply(BaseApplyRequestDto baseApplyRequestDto, Group group) {
        CodeApply codeApply = new CodeApply();
        codeApply.setCode(baseApplyRequestDto.getContent());
        codeApply.setGroup(group);
        // generate Code
        return codeApplyRepository.save(codeApply);
    }
}
