package com.depromeet.watni.domain.manager.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Table(name = "manager")
@Entity
@Builder
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public class Manager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private long managerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
