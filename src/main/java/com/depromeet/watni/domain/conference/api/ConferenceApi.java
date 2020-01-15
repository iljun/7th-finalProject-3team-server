package com.depromeet.watni.domain.conference.api;

import com.depromeet.watni.domain.conference.service.ConferenceService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConferenceApi {
    private final ConferenceService conferenceService;
    public ConferenceApi(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

}
