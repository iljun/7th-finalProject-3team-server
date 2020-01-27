package com.depromeet.watni.domain.accession.api;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.dto.AccessionDto;
import com.depromeet.watni.domain.accession.dto.AccessionResponseDto;
import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.groupcode.GroupCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccessionApi {
    private final AccessionService accessionService;
    private final GroupCodeService groupCodeService;
    public AccessionApi(AccessionService accessionService,
                        GroupCodeService groupCodeService) {
        this.accessionService = accessionService;
        this.groupCodeService = groupCodeService;
    }

    @PostMapping("/api/group/{groupId}/accession")
    public ResponseEntity accessGroup(@PathVariable Long groupId, @RequestBody AccessionDto accessionDto) {

        List<Accession> accessions= accessionService.accessGroup(groupId, accessionDto.getMemberIdList());
        // group exception
        // group -> code exception
        // code / auto -> create Accession
        // return GROUP, SESSION
        List<AccessionResponseDto> result = new ArrayList<AccessionResponseDto>();
        for (Accession accession : accessions) {
            result.add(accession.toResponseDto());
        }
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/api/group/{groupId}/accession/member/{memberId}")
    public ResponseEntity deleteAccess(@PathVariable Long groupId,@PathVariable Long memberId){
        accessionService.deleteAccess(groupId,memberId);
        return ResponseEntity.accepted().build();
    }
}
