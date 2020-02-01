package com.depromeet.watni.domain.attendance.dto;

import com.depromeet.watni.domain.attendance.constant.AttendanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PhotoAttendanceResponseDto extends BaseAttendanceResponseDto {

    private AttendanceType attendanceType = AttendanceType.PHOTO;
    private String imageUrl;

}
