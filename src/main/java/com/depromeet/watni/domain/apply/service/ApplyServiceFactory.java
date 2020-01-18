package com.depromeet.watni.domain.apply.service;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import org.springframework.stereotype.Component;

@Component
public class ApplyServiceFactory {

    private final CodeApplyService codeApplyService;
    public ApplyServiceFactory(CodeApplyService codeApplyService) {
        this.codeApplyService = codeApplyService;
    }

    public ApplyService generateApplyService(ApplyType applyType) {
        switch (applyType) {
            case CODE:
                return this.codeApplyService;
            default:
                throw new IllegalArgumentException("not support applyType");
        }
    }
}
