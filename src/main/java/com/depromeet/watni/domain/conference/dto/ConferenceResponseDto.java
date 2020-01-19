package com.depromeet.watni.domain.conference.dto;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ConferenceResponseDto {

    private long conferenceId;

    private String name;

    private String description;

    private LocalDateTime startAt;

    private LocalDateTime endAt;
}
