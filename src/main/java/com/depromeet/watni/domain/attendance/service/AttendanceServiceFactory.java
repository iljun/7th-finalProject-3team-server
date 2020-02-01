package com.depromeet.watni.domain.attendance.service;

import com.depromeet.watni.domain.attendance.constant.AttendanceType;
import org.springframework.stereotype.Component;

@Component
public class AttendanceServiceFactory {
    private final PhotoAttendanceService photoAttendanceService;
    public AttendanceServiceFactory(PhotoAttendanceService photoAttendanceService) {
        this.photoAttendanceService = photoAttendanceService;
    }

    public AttendanceService createAttendanceService(AttendanceType attendanceType) {
        switch (attendanceType) {
            case PHOTO:
                return this.photoAttendanceService;
        }
        throw new IllegalArgumentException("not support attendanceType");
    }
}
