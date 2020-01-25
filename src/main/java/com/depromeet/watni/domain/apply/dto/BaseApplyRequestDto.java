package com.depromeet.watni.domain.apply.dto;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import lombok.Getter;

@Getter
public class BaseApplyRequestDto {
    private ApplyType applyType;
    private String content;
}
