package com.depromeet.watni.domain.accession.service;

import com.depromeet.watni.domain.accession.repository.AccessionRepository;
import org.springframework.stereotype.Service;

@Service
public class AccessionService {
    private final AccessionRepository accessionRepository;
    public AccessionService(AccessionRepository accessionRepository) {
        this.accessionRepository = accessionRepository;
    }
}
