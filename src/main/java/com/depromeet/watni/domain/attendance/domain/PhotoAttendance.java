package com.depromeet.watni.domain.attendance.domain;

import com.depromeet.watni.domain.attendance.constant.AttendanceStatus;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.member.domain.Member;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Photo")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@EqualsAndHashCode(callSuper=true)
public class PhotoAttendance extends BaseAttendance {

    @Column(name = "photo_url")
    private String photoUrl;

    @Builder
    public PhotoAttendance(long attendanceId, Member member, AttendanceStatus attendanceStatus, LocalDateTime attendanceAt, Conference conference, String photoUrl) {
        super(attendanceId, member, attendanceStatus, attendanceAt, conference);
        this.photoUrl = photoUrl;
    }
}
