package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.repository.ApplyRepository;
import com.depromeet.watni.domain.apply.repository.CodeApplyRepository;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.exception.NotFoundException;
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
        if(codeApplyRepository.findOneByGroup(group).isPresent())
            throw new BadRequestException("Already Code Apply Exist");
        CodeApply codeApply = new CodeApply();
        codeApply.setCode(baseApplyRequestDto.getContent());
        codeApply.setGroup(group);
        return codeApplyRepository.save(codeApply);
    }

    @Override
    public BaseApply getApply(BaseApplyRequestDto baseApplyRequestDto, Group group) {
        BaseApply baseApply = codeApplyRepository.findOneByGroup(group).orElseThrow(()->new NotFoundException("NOT FOUND CODE APPLY"));
        return baseApply;
    }

    @Override
    public void checkApply(BaseApplyRequestDto baseApplyRequestDto, Group group){
        CodeApply codeApply = (CodeApply) this.getApply(baseApplyRequestDto,group);
        if (! codeApply.getCode().equals(baseApplyRequestDto.getContent()))
            throw new BadRequestException("Wrong Apply code");
    }

}
