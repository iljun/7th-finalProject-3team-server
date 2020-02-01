package com.depromeet.watni.domain.attendance.dto;

import com.depromeet.watni.domain.attendance.constant.AttendanceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseAttendanceResponseDto {
    private long attendanceId;
    private AttendanceStatus attendanceStatus;
    private LocalDateTime attendanceAt;
    private String name;
}
