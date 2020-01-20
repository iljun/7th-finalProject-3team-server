package com.depromeet.watni.domain.attendance.repository;

import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository<T extends BaseAttendance> extends JpaRepository<BaseAttendance, Long> {
}
