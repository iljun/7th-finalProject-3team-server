package com.depromeet.watni.domain.manager.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.group.domain.Group;
<<<<<<< HEAD
import lombok.*;
=======
import com.depromeet.watni.domain.member.domain.Member;
>>>>>>> add domain relationship

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

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
