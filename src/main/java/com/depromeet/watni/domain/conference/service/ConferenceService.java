package com.depromeet.watni.domain.conference.service;

import com.depromeet.watni.domain.conference.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }
}
