package com.depromeet.watni.domain.conference.dto;

import com.depromeet.watni.domain.conference.domain.Conference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConferenceMapper {

    @Mapping(target = "conferenceId", source = "conferenceId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "locationInfo", source = "locationInfo")
    @Mapping(target = "startAt", source = "startAt")
    @Mapping(target = "endAt", source = "endAt")
    @Mapping(target = "photoUrl", source = "photoUrl")
    @Mapping(target = "notice", source = "notice")
    ConferenceResponseDto converter(Conference conference);
}
