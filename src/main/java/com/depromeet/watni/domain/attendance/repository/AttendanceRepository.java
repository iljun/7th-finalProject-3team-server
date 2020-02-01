package com.depromeet.watni.domain.attendance.repository;

import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository<T extends BaseAttendance> extends JpaRepository<BaseAttendance, Long> {
    Optional<T> findByConferenceAndMember(Conference conference, Member member);

    Optional<List<T>> findByConference(Conference conference);
}
