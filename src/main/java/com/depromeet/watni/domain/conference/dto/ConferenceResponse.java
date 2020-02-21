package com.depromeet.watni.domain.conference.dto;

import com.depromeet.watni.domain.attendance.dto.AttendanceResponse;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConferenceResponse {

    private long conferenceId;

    private String name;

    private String description;

    private String locationInfo;

    private String photoUrl;

    private String notice;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private List<AttendanceResponse> attendances;

    public ConferenceResponse(Conference conference){
        this.conferenceId = conference.getConferenceId();
        this.name = conference.getName();
        this.description = conference.getDescription();
        this.locationInfo = conference.getLocationInfo();
        this.photoUrl = conference.getPhotoUrl();
        this.notice = conference.getNotice();
        this.startAt = conference.getStartAt();
        this.endAt = conference.getEndAt();
        this.attendances = conference.getAttendances().stream().map(attendance -> new AttendanceResponse(attendance)).collect(Collectors.toList());
    }

    public ConferenceResponse(Conference conference, Member member){
        this.conferenceId = conference.getConferenceId();
        this.name = conference.getName();
        this.description = conference.getDescription();
        this.locationInfo = conference.getLocationInfo();
        this.photoUrl = conference.getPhotoUrl();
        this.notice = conference.getNotice();
        this.startAt = conference.getStartAt();
        this.endAt = conference.getEndAt();
        this.attendances = conference.getAttendances().stream().filter(attendance -> member == attendance.getMember()).map(attendance -> new AttendanceResponse(attendance)).collect(Collectors.toList());
    }
}
