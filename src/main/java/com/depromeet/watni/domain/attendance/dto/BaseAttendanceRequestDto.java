package com.depromeet.watni.domain.attendance.dto;

import com.depromeet.watni.domain.attendance.constant.AttendanceType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "attendanceType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhotoAttendanceRequestDto.class, name = "PHOTO")
})
public abstract class BaseAttendanceRequestDto {
    private AttendanceType attendanceType;
}
