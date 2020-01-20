package com.depromeet.watni.domain.attendance.service;

import com.depromeet.watni.domain.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

}
