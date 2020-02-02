package com.depromeet.watni.domain.group.api;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupApi {
    private final GroupService groupService;
    private final GroupGenerateService groupGenerateService;
    public GroupApi(GroupService groupService,
                    GroupGenerateService groupGenerateService) {
        this.groupService = groupService;
        this.groupGenerateService = groupGenerateService;
    }


    @GetMapping("/api/group/{groupId}")
    public ResponseEntity getGroup(@PathVariable Long groupId,
                                   @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.getGroup(groupId);
        group.isAccessionUser(memberDetail);
        return ResponseEntity.ok().body(group.toResponseDto());
    }

    @PostMapping("/api/group")
    public ResponseEntity createGroup(@RequestBody GroupDto groupDto,
                                      @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupGenerateService.createGroup(groupDto, memberDetail);
        GroupResponseDto result = group.toResponseDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("api/group/{groupId}")
    public ResponseEntity deleteGroup(@PathVariable Long groupId,
                                      @AuthenticationPrincipal MemberDetail memberDetail){
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        groupService.delete(group);
        return ResponseEntity.accepted().build();
    }

//    @PostMapping("/api/groups/access")
//    public ResponseEntity accessGroupByCode(@RequestBody AccessGroupRequestDto accessGroupRequestDto) {
//        groupCodeService.checkGroupCode(accessGroupRequestDto.getGroupId(), accessGroupRequestDto.getAccessCode());
//
//        List<Accession> accessions = accessionService.accessGroup(accessGroupRequestDto.getGroupId(),
//                accessGroupRequestDto.getMemberIdList());
//
//        List<AccessionResponseDto> result = new ArrayList<AccessionResponseDto>();
//        for (Accession accession : accessions) {
//            result.add(accession.toResponseDto());
//        }
//        return ResponseEntity.ok().body(result);
//    }
}
