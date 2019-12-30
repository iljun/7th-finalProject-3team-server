package com.example.demo.domain.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository<T extends BaseAttendance> extends JpaRepository<BaseAttendance, Long> {
}
