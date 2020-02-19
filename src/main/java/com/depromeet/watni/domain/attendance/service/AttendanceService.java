package com.depromeet.watni.domain.attendance.service;

import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.attendance.dto.BaseAttendanceRequestDto;
import com.depromeet.watni.domain.attendance.repository.AttendanceRepository;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

public interface AttendanceService<T extends BaseAttendance, E extends BaseAttendanceRequestDto> {

    AttendanceRepository getAttendanceRepository();

    T createAttendance(E requestDto, Conference conference, Member member);

    default List<T> getAttendances(Conference conference) {
        return (List<T>) getAttendanceRepository().findByConference(conference)
                .orElse(new ArrayList());
    }
}
