package com.depromeet.watni.domain.conference.api;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.conference.dto.ConferenceMapper;
import com.depromeet.watni.domain.conference.dto.ConferenceRequestDto;
import com.depromeet.watni.domain.conference.dto.ConferenceResponseDto;
import com.depromeet.watni.domain.conference.service.ConferenceService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ConferenceApi {
    private final static ConferenceMapper CONFERENCE_MAPPER = Mappers.getMapper(ConferenceMapper.class);

    private final ConferenceService conferenceService;
    private final GroupService groupService;
    public ConferenceApi(ConferenceService conferenceService,
                         GroupService groupService) {
        this.conferenceService = conferenceService;
        this.groupService = groupService;
    }

    @GetMapping("/api/group/{groupId}/conferences")
    public ResponseEntity getConferences(@PathVariable long groupId,
                                         @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAccessionUser(memberDetail);
        List<ConferenceResponseDto> responseDtos = group.getConferences()
                .stream()
                .map(c -> CONFERENCE_MAPPER.converter(c))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/api/group/{groupId}/conference/{conferenceId}")
    public ResponseEntity getConference(@PathVariable long groupId,
                                        @PathVariable long conferenceId,
                                        @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAccessionUser(memberDetail);
        if (group.getConferences().isEmpty()) {
            throw new BadRequestException("NOT FOUND CONFERENCE");
        }
        ConferenceResponseDto responseDto = group.getConferences()
                .stream()
                .filter(c -> c.getConferenceId() == conferenceId)
                .findFirst()
                .map(c -> CONFERENCE_MAPPER.converter(c))
                .orElseThrow(() -> new BadRequestException("NOT FOUND CONFERENCE"));
        return ResponseEntity.ok(responseDto);

    }

    @PostMapping("/api/group/{groupId}/conference")
    public ResponseEntity generateConference(@PathVariable long groupId,
                                             @RequestBody @Validated({ConferenceRequestDto.create.class}) ConferenceRequestDto conferenceRequestDto,
                                             @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        Conference conference = conferenceService.generateConference(conferenceRequestDto, group);
        return ResponseEntity.ok(CONFERENCE_MAPPER.converter(conference));

    }

    @PatchMapping("/api/group/{groupId}/conference/{conferenceId}")
    public ResponseEntity updateConference(@PathVariable long groupId,
                                           @PathVariable long conferenceId,
                                           @RequestBody @Validated({ConferenceRequestDto.update.class}) ConferenceRequestDto conferenceRequestDto,
                                           @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        Conference originConference = group.containsConference(conferenceId);
        Conference conference = conferenceService.updateConference(conferenceRequestDto, originConference);
        return ResponseEntity.ok(CONFERENCE_MAPPER.converter(conference));

    }

    @DeleteMapping("/api/group/{groupId}/conference/{conferenceId}")
    public ResponseEntity deleteConference(@PathVariable long groupId,
                                           @PathVariable long conferenceId,
                                           @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAdministrator(memberDetail);
        Conference originConference = group.containsConference(conferenceId);
        conferenceService.deleteConference(originConference);
        return ResponseEntity.accepted().build();
    }
}
