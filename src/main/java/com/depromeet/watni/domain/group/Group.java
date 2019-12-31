package com.depromeet.watni.domain.group;

import com.depromeet.watni.domain.conference.Conference;
import com.depromeet.watni.domain.manager.Manager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "groups")
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Conference> conferences = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Manager> managers = new ArrayList<>();


    // TODO createdAt, modifiedAt
}
