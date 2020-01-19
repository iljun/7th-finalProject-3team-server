package com.depromeet.watni.domain.group.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.apply.domain.BaseApply;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.group.dto.GroupResponseDto;
import com.depromeet.watni.domain.manager.domain.Manager;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.exception.BadRequestException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "groups")
@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "group")
    @Builder.Default
    private List<Conference> conferences = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @Builder.Default
    private List<Manager> managers = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    @Builder.Default
    private List<Accession> accessions = new ArrayList<>();

    @OneToOne(mappedBy = "group")
    @Setter
    private BaseApply baseApply;

    //map struct
    public GroupResponseDto toResponseDto() {
    	return GroupResponseDto.builder().groupId(this.groupId).name(this.name).conferences(this.conferences).build();
    }

    public void isAdministrator(MemberDetail memberDetail) {
        managers
                .stream()
                .filter(m -> m.getManagerId() == memberDetail.getMemberId())
                .findAny()
                .orElseThrow(() -> new BadRequestException("NOT ADMINISTRATOR"));
    }
}
