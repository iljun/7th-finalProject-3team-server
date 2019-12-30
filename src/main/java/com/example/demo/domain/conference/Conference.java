package com.example.demo.domain.conference;

import com.example.demo.domain.attendance.BaseAttendance;
import com.example.demo.domain.group.Group;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "conference")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private long id;

    @Column(name = "name")
    private String name;

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
