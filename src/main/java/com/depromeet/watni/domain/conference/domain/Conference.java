package com.depromeet.watni.domain.conference.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.group.domain.Group;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "conference")
@Entity
public class Conference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "conference")
    private List<BaseAttendance> attendances = new ArrayList<>();

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    // TODO 위치정보

    // TODO cretedAt, modifeidAt
}
