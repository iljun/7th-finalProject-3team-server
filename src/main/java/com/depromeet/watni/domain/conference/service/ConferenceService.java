package com.depromeet.watni.domain.conference.service;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.conference.dto.ConferenceRequestDto;
import com.depromeet.watni.domain.conference.repository.ConferenceRepository;
import com.depromeet.watni.domain.group.domain.Group;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference generateConference(ConferenceRequestDto conferenceRequestDto, Group group) {
        return conferenceRepository.save(conferenceRequestDto.toEntity(group));
    }

    public Conference updateConference(ConferenceRequestDto conferenceRequestDto, Conference conference) {
        return conferenceRepository.save(conferenceRequestDto.toEntity(conference));
    }

    public void deleteConference(Conference conference) {
        conference.delete();
        conferenceRepository.save(conference);
    }
}
