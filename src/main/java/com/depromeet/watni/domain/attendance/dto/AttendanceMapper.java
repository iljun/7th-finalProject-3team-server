package com.depromeet.watni.domain.attendance.dto;

import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.attendance.domain.PhotoAttendance;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttendanceMapper {
    public static AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);


    @InheritInverseConfiguration
    @Mapping(target = "attendanceId", source = "id")
    @Mapping(target = "attendanceStatus", source = "attendanceStatus")
    @Mapping(target = "imageUrl", source = "photoUrl")
    @Mapping(target = "attendanceAt", source = "attendanceAt")
    @Mapping(target = "name", source = "member.name")
    @Mapping(target = "attendanceType", source = "", ignore = true)
    PhotoAttendanceResponseDto map(PhotoAttendance photoAttendance);

    default BaseAttendanceResponseDto map(BaseAttendance baseAttendance) {
        if (baseAttendance instanceof PhotoAttendance) {
            return this.map((PhotoAttendance) baseAttendance);
        }
        throw new IllegalArgumentException("not support dto");
    }

}