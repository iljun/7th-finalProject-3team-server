package com.depromeet.watni.domain.conference.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConferenceResponseDto {

    private long conferenceId;

    private String name;

    private String description;

    private String locationInfo;

    private LocalDateTime startAt;

    private LocalDateTime endAt;
}
