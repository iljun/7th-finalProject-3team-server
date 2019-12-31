package com.depromeet.watni.domain.manager;

import com.depromeet.watni.domain.group.Group;

import javax.persistence.*;

@Table(name = "manager")
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private long managerId;

    @Column(name = "member_id")
    private long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    // TODO createdAt, modifiedAt
}
