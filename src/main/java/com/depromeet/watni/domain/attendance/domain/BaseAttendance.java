package com.depromeet.watni.domain.attendance.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.attendance.constant.AttendanceStatus;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "attendance")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public abstract class BaseAttendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "attendance_status")
    @Enumerated(value = EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "attendance_at")
    private LocalDateTime attendanceAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;

}
