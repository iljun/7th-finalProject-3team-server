package com.depromeet.watni.domain.apply.api;

import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.service.ApplyService;
import com.depromeet.watni.domain.apply.service.ApplyServiceFactory;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        // TODO refactoring
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        ApplyService applyService = applyServiceFactory.generateApplyService(baseApplyRequestDto.getApplyType());
        BaseApply baseApply = applyService.generateApply(baseApplyRequestDto);
        group.setBaseApply(baseApply);
        groupService.update(group);
        return ResponseEntity.ok().build();
    }
}
