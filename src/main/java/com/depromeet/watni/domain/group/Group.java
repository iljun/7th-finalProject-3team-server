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

import com.depromeet.watni.domain.accession.Accession;
import com.depromeet.watni.domain.accession.AccessionRole;
import com.depromeet.watni.domain.accession.AccessionStatus;
import com.depromeet.watni.domain.accession.AccessionType;
import com.depromeet.watni.domain.accession.Accession.AccessionBuilder;
import com.depromeet.watni.domain.conference.Conference;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.member.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "groups")
@Entity
@Builder
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
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
   
    public GroupResponseDto toResponseDto() {
    	return GroupResponseDto.builder().id(this.id).name(this.name).code(this.code).conferences(this.conferences).build();
    }

    // TODO createdAt, modifiedAt
}
