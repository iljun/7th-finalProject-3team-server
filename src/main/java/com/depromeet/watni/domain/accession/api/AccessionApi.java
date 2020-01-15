package com.depromeet.watni.domain.accession.api;

import com.depromeet.watni.domain.accession.service.AccessionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessionApi {
    private final AccessionService accessionService;
    public AccessionApi(AccessionService accessionService) {
        this.accessionService = accessionService;
    }
}
