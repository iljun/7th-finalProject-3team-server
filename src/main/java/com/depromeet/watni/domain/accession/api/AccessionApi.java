package com.depromeet.watni.domain.accession.api;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.accession.dto.AccessionDto;
import com.depromeet.watni.domain.accession.dto.AccessionResponseDto;
import com.depromeet.watni.domain.accession.service.AccessionService;
import com.depromeet.watni.domain.groupcode.GroupCodeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public List<AccessionResponseDto> accessGroup(@PathVariable Long groupId, @RequestBody AccessionDto accessionDto) {
        groupCodeService.checkGroupCode(groupId, accessionDto.getCode());
        List<Accession> accessions= accessionService.accessGroupByCode(groupId, accessionDto.getMemberIdList());
        // group exception
        // group -> code exception
        // code / auto -> create Accession
        // return GROUP, SESSION
        List<AccessionResponseDto> result = new ArrayList<AccessionResponseDto>();
        for (Accession accession : accessions) {
            result.add(accession.toResponseDto());
        }
        return result;
    }
}
