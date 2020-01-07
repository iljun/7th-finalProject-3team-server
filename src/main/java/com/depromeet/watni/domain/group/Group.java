package com.depromeet.watni.domain.group;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.depromeet.watni.domain.conference.Conference;
import com.depromeet.watni.domain.manager.Manager;

import lombok.Builder;
import lombok.Data;

@Table(name = "groups")
@Entity
@Builder
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Conference> conferences = new ArrayList<>();

    /*
    @OneToMany(mappedBy = "group")
    private List<Manager> managers = new ArrayList<>();
    */
    
    private String code;

    // TODO createdAt, modifiedAt
}
