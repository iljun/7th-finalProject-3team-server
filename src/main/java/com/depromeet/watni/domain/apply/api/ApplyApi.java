package com.depromeet.watni.domain.apply.api;

import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.domain.CodeApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.dto.CodeApplyResponseDto;
import com.depromeet.watni.domain.apply.service.ApplyService;
import com.depromeet.watni.domain.apply.service.ApplyServiceFactory;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class ApplyApi {

    private final ApplyServiceFactory applyServiceFactory;
    private final GroupService groupService;
    private final AccessionService accessionService;
    public ApplyApi(ApplyServiceFactory applyServiceFactory,
                    GroupService groupService, AccessionService accessionService) {
        this.applyServiceFactory = applyServiceFactory;
        this.groupService = groupService;
        this.accessionService = accessionService;
    }

    @PostMapping("/api/group/{groupId}/apply-way")
    public ResponseEntity generateApply(@PathVariable long groupId,
                                        @AuthenticationPrincipal MemberDetail memberDetail,
                                        @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        BaseApply baseApply = applyService.generateApply(baseApplyRequestDto,group);
        CodeApplyResponseDto result = new CodeApplyResponseDto((CodeApply) baseApply);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/api/group/{groupId}/apply-way")
    public ResponseEntity getApply(@PathVariable long groupId,
                                   @AuthenticationPrincipal MemberDetail memberDetail,
                                        @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        Group group = groupService.selectGroupByGroupId(groupId);
        
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        BaseApply baseApply = applyService.getApply(baseApplyRequestDto,group);
        CodeApplyResponseDto result = new CodeApplyResponseDto((CodeApply) baseApply);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/api/group/{groupId}/apply-way/check")
    public ResponseEntity checkApply(@PathVariable long groupId,
                                     @AuthenticationPrincipal MemberDetail memberDetail,
                                   @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        Group group = groupService.selectGroupByGroupId(groupId);
        //group.isAdministrator(memberDetail);
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        applyService.checkApply(baseApplyRequestDto,group);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/api/group/apply-way/confirm")
    public ResponseEntity confirm(@AuthenticationPrincipal MemberDetail memberDetail,
                                     @RequestBody BaseApplyRequestDto baseApplyRequestDto) {
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        GroupResponseDto group = applyService.confirmApply(baseApplyRequestDto);
        accessionService.accessGroup(group.getGroupId(), Collections.singletonList(memberDetail.getMemberId()));
        return ResponseEntity.accepted().body(group);
    }
}
