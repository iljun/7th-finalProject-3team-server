package com.depromeet.watni.domain.apply.api;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.dto.CodeApplyResponseDto;
import com.depromeet.watni.domain.apply.service.ApplyService;
import com.depromeet.watni.domain.apply.service.ApplyServiceFactory;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplyApi {

    private final ApplyServiceFactory applyServiceFactory;
    private final GroupService groupService;
    public ApplyApi(ApplyServiceFactory applyServiceFactory,
                    GroupService groupService) {
        this.applyServiceFactory = applyServiceFactory;
        this.groupService = groupService;
    }

    @PostMapping("/api/group/{groupId}/apply-way")
    public ResponseEntity generateApply(@PathVariable long groupId,
                                        @AuthenticationPrincipal MemberDetail memberDetail,
                                        @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        BaseApply baseApply = applyService.generateApply(baseApplyRequestDto.getContent(),group);
        CodeApplyResponseDto result = new CodeApplyResponseDto((CodeApply) baseApply);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/api/group/{groupId}/apply-way")
    public ResponseEntity getApply(@PathVariable long groupId,
                                        @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        Group group = groupService.selectGroupByGroupId(groupId);
        
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        BaseApply baseApply = applyService.getApply(baseApplyRequestDto.getContent(),group);
        CodeApplyResponseDto result = new CodeApplyResponseDto((CodeApply) baseApply);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/api/group/{groupId}/apply-way/check")
    public ResponseEntity checkApply(@PathVariable long groupId,

                                   @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        Group group = groupService.selectGroupByGroupId(groupId);
        //group.isAdministrator(memberDetail);
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        applyService.checkApply(baseApplyRequestDto.getContent(),group);
        return ResponseEntity.accepted().build();
    }
}
