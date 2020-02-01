package com.depromeet.watni.domain.attendance.api;

import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.attendance.dto.AttendanceMapper;
import com.depromeet.watni.domain.attendance.dto.BaseAttendanceRequestDto;
import com.depromeet.watni.domain.attendance.service.AttendanceService;
import com.depromeet.watni.domain.attendance.service.AttendanceServiceFactory;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.service.MemberService;
import com.depromeet.watni.exception.BadRequestException;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AttendanceApi<T extends BaseAttendanceRequestDto, E extends BaseAttendance> {
    private static final AttendanceMapper ATTENDANCE_MAPPER =  Mappers.getMapper(AttendanceMapper.class);
    private final AttendanceServiceFactory attendanceServiceFactory;
    private final GroupService groupService;
    private final AttendanceService attendanceService;
    private final MemberService memberService;
    public AttendanceApi(AttendanceServiceFactory attendanceServiceFactory,
                         GroupService groupService,
                         AttendanceService attendanceService,
                         MemberService memberService) {
        this.attendanceServiceFactory = attendanceServiceFactory;
        this.groupService = groupService;
        this.attendanceService = attendanceService;
        this.memberService = memberService;
    }

    @PostMapping("/api/group/{groupId}/conference/{conferenceId}/attendance")
    public ResponseEntity createAttendance(@PathVariable long groupId,
                                           @PathVariable long conferenceId,
                                           @RequestBody T requestDto,
                                           @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAccessionUser(memberDetail);
        Conference conference = group.getConferences()
                .stream()
                .filter(c -> c.getConferenceId() == conferenceId)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("NOT FOUND CONFERENCE"));
        conference.availableAttendance();

        AttendanceService attendanceService = attendanceServiceFactory.createAttendanceService(requestDto.getAttendanceType());
        E baseAttendance = (E) attendanceService.createAttendance(requestDto, conference, memberService.selectByMemberId(memberDetail.getMemberId()));
        return new ResponseEntity(ATTENDANCE_MAPPER.map(baseAttendance), HttpStatus.CREATED);
    }

    @GetMapping("/api/group/{groupId}/conference/{conferenceId}/attendances")
    public ResponseEntity getAttendances(@PathVariable long groupId,
                                         @PathVariable long conferenceId,
                                         @AuthenticationPrincipal MemberDetail memberDetail) {
        Group group = groupService.selectGroupByGroupId(groupId);
        group.isAccessionUser(memberDetail);
        Conference conference = group.getConferences()
                .stream()
                .filter(c -> c.getConferenceId() == conferenceId)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("NOT FOUND CONFERENCE"));
        List<E> attendances = attendanceService.getAttendances(conference);
        // member정보 필요
        return ResponseEntity.ok(attendances
                .stream()
                .map(a -> ATTENDANCE_MAPPER.map(a))
                .collect(Collectors.toList())
        );
    }
}
