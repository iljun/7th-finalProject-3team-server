package com.depromeet.watni.domain.attendance.api;

import com.depromeet.watni.domain.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttendanceApi {
    private final AttendanceService attendanceService;
    public AttendanceApi(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
}
