package com.depromeet.watni.domain.manager.api;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.manager.domain.Manager;
import com.depromeet.watni.domain.manager.dto.ManagerDto;
import com.depromeet.watni.domain.manager.dto.ManagerMapper;
import com.depromeet.watni.domain.manager.service.ManagerService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.service.MemberService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerApi {
    private final static ManagerMapper MANAGER_MAPPER = Mappers.getMapper(ManagerMapper.class);

    private final ManagerService managerService;
    private final GroupService groupService;
    private final MemberService memberService;
    public ManagerApi(ManagerService managerService,
                      GroupService groupService,
                      MemberService memberService) {
        this.managerService = managerService;
        this.groupService = groupService;
        this.memberService = memberService;
    }

    @GetMapping("/api/group/{groupId}/managers")
    public ResponseEntity getManagerByGroup(@PathVariable long groupId,
                                            @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        return ResponseEntity.ok(group.getManagers()
                .stream()
                .map(m -> MANAGER_MAPPER.convert(m))
        );
    }

    @PostMapping("/api/group/{groupId}/manager")
    public ResponseEntity registerManager(@PathVariable long groupId,
                                          @RequestBody ManagerDto managerDto,
                                          @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        memberDetail.isAdministrator(group);
        Member member = memberService.selectByMemberId(managerDto.getManagerId());
        Manager manger = managerService.registerManager(group, member);
        return ResponseEntity.ok(MANAGER_MAPPER.convert(manger, group));
    }

    @DeleteMapping("/api/group/{groupId}/manager/{managerId}")
    public ResponseEntity deleteManager(@PathVariable long groupId,
                                        @PathVariable long managerId,
                                        @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        memberDetail.isAdministrator(group);
        managerService.deleteManager(group, managerId);
        return ResponseEntity.accepted().build();
    }

}
