package com.depromeet.watni.domain.attendance.dto;

import com.depromeet.watni.domain.attendance.constant.AttendanceStatus;
import com.depromeet.watni.domain.attendance.constant.AttendanceType;
import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.attendance.domain.PhotoAttendance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AttendanceResponse {
    private long attendanceId;
    private AttendanceStatus attendanceStatus;
    private LocalDateTime attendanceAt;
    private AttendanceType attendanceType = AttendanceType.PHOTO;
    private String imageUrl;

    public AttendanceResponse(BaseAttendance baseAttendance){
        this.attendanceId = baseAttendance.getId();
        this.attendanceStatus = baseAttendance.getAttendanceStatus();
        this.attendanceAt = baseAttendance.getAttendanceAt();
        this.attendanceType = AttendanceType.PHOTO;
        this.imageUrl = ((PhotoAttendance)baseAttendance).getPhotoUrl();
    }

}
