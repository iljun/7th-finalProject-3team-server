package com.depromeet.watni.domain.conference.dto;

import com.depromeet.watni.domain.conference.domain.Conference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConferenceMapper {

    @Mapping(target = "conferenceId", source = "conferenceId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "startAt", source = "startAt", ignore = true)
    @Mapping(target = "endAt", source = "endAt", ignore = true)
    ConferenceResponseDto converter(Conference conference);
}
