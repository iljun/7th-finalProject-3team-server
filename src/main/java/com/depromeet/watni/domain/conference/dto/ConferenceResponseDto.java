package com.depromeet.watni.domain.conference.dto;

import com.depromeet.watni.domain.conference.domain.Conference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConferenceResponseDto {

    private long conferenceId;

    private String name;

    private String description;

    private String locationInfo;

    private String photoUrl;

    private String notice;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    public ConferenceResponseDto(Conference conference){
        this.conferenceId = conference.getConferenceId();
        this.name = conference.getName();
        this.description = conference.getDescription();
        this.locationInfo = conference.getLocationInfo();
        this.photoUrl = conference.getPhotoUrl();
        this.notice = conference.getNotice();
        this.startAt = conference.getStartAt();
        this.endAt = conference.getEndAt();
    }
}
