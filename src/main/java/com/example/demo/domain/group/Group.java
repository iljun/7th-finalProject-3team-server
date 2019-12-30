package com.example.demo.domain.group;

import com.example.demo.domain.conference.Conference;
import com.example.demo.domain.manager.Manager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "group")
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
