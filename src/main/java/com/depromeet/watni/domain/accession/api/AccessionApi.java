package com.depromeet.watni.domain.accession.api;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.dto.AccessionDto;
import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.apply.service.ApplyServiceFactory;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.groupcode.GroupCodeService;
import com.depromeet.watni.domain.member.MemberDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccessionApi {
    private final ApplyServiceFactory applyServiceFactory;
    private final AccessionService accessionService;
    private final GroupCodeService groupCodeService;
    public AccessionApi(ApplyServiceFactory applyServiceFactory, AccessionService accessionService,
                        GroupCodeService groupCodeService) {
        this.applyServiceFactory = applyServiceFactory;
        this.accessionService = accessionService;
        this.groupCodeService = groupCodeService;
    }

    @PostMapping("/api/group/accession")
    public ResponseEntity accessGroup(@AuthenticationPrincipal MemberDetail memberDetail, @RequestBody AccessionDto accessionDto) {
        Accession accession= accessionService.accessGroup(accessionDto,memberDetail.getMemberId());
        GroupResponseDto group = new GroupResponseDto(accession.getGroup());
        return ResponseEntity.accepted().body(group);
    }

    @DeleteMapping("/api/group/{groupId}/accession/member/{memberId}")
    public ResponseEntity deleteAccess(@PathVariable Long groupId,@PathVariable Long memberId){
        accessionService.deleteAccess(groupId,memberId);
        return ResponseEntity.accepted().build();
    }
}
