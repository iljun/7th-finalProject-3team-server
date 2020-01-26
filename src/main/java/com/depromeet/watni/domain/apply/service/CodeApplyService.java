package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.repository.ApplyRepository;
import com.depromeet.watni.domain.apply.repository.CodeApplyRepository;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.exception.BadRequestException;
import org.aspectj.apache.bcel.classfile.Code;
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
        //codeApply.setApplyType(baseApplyRequestDto.getApplyType());
        // generate Code
        return codeApplyRepository.save(codeApply);
    }

    @Override
    public BaseApply getApply(BaseApplyRequestDto baseApplyRequestDto, Group group) {
        BaseApply baseApply = codeApplyRepository.findOneByGroup(group);
        return baseApply;
    }

    @Override
    public void checkApply(BaseApplyRequestDto baseApplyRequestDto, Group group){
        CodeApply codeApply = (CodeApply) this.getApply(baseApplyRequestDto,group);
        if (! codeApply.getCode().equals(baseApplyRequestDto.getContent()))
            throw new BadRequestException("wrong code");
    }

}
