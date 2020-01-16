package com.depromeet.watni.domain.participationUser.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.member.domain.Member;

import javax.persistence.*;

@Table(name = "participation_user")
@Entity
public class ParticipationUser extends BaseEntity {

    @Id
    @Column(name = "participation_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long participationUserId;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
