package com.depromeet.watni.domain.group.api;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.dto.AccessionResponseDto;
import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.AccessGroupRequestDto;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.groupcode.GroupCode;
import com.depromeet.watni.domain.groupcode.GroupCodeService;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupApi {
    private final GroupService groupService;
    private final AccessionService accessionService;
    private final GroupCodeService groupCodeService;
    public GroupApi(GroupService groupService,
                    AccessionService accessionService,
                    GroupCodeService groupCodeService) {
        this.groupService = groupService;
        this.accessionService = accessionService;
        this.groupCodeService = groupCodeService;
    }


    @GetMapping("/api/group/{groupId}")
    public ResponseEntity getGroup(@PathVariable Long groupId) {
        GroupResponseDto result = groupService.getGroup(groupId).toResponseDto();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/api/group")
    public ResponseEntity createGroup(@RequestBody GroupDto groupDto) {
        Group group = groupService.createGroup(groupDto);
        GroupResponseDto result = group.toResponseDto();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("api/group/{groupId}")
    public ResponseEntity deleteGroup(@PathVariable Long groupId, @AuthenticationPrincipal MemberDetail memberDetail){
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
