package com.example.demo.domain.manager;

import com.example.demo.domain.group.Group;

import javax.persistence.*;

@Table
@Entity(name = "manager")
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
