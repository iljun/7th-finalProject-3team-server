package com.depromeet.watni.domain.apply.dto;

import com.depromeet.watni.domain.apply.domain.CodeApply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeApplyResponseDto {
    private String code;

    public CodeApplyResponseDto(CodeApply codeApply){
        this.code = codeApply.getCode();
    }
}
