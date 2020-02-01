package com.depromeet.watni.domain.attendance.dto;

import com.depromeet.watni.domain.attendance.constant.AttendanceType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonTypeName("PHOTO")
@EqualsAndHashCode(callSuper=false)
public class PhotoAttendanceRequestDto extends BaseAttendanceRequestDto {
    private AttendanceType attendanceType = AttendanceType.PHOTO;
    private String base64Image;
}
