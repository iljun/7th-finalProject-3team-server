package com.depromeet.watni.domain.member.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.member.dto.MemberRequestDto;
import lombok.*;

import javax.persistence.*;

@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long memberId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    @Builder.Default
//    private List<Manager> managers = new ArrayList<>();

    public static Member of(MemberRequestDto memberRequestDto) {
        return Member
                .builder()
                .email(memberRequestDto.getEmail())
                .name(memberRequestDto.getName())
                .password(memberRequestDto.getPassword())
                .build();
    }
}
