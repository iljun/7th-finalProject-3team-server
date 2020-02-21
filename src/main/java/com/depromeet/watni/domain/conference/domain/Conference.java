package com.depromeet.watni.domain.conference.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.exception.BadRequestException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "conference")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Conference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private long conferenceId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location_info")
    private String locationInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "conference")
    @Builder.Default
    private List<BaseAttendance> attendances = new ArrayList<>();

    @Column(name = "start_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime startAt;

    @Column(name = "end_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime endAt;

    @Setter
    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "notice")
    private String notice;

    public void availableAttendance() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(this.startAt) || now.isAfter(this.endAt)) {
            throw new BadRequestException("Attendance check is unavailable");
        }
    }
}
