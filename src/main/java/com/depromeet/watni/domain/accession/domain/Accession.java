package com.depromeet.watni.domain.accession.domain;

import com.depromeet.watni.base.BaseEntity;
import com.depromeet.watni.domain.accession.constant.AccessionStatus;
import com.depromeet.watni.domain.accession.constant.AccessionType;
import com.depromeet.watni.domain.accession.dto.AccessionResponseDto;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.member.domain.Member;
import lombok.*;
import javax.persistence.*;

@Table(name = "accession")
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Accession extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accession_id")
	private long id;

	@Column(name = "accession_type")
	@Enumerated(value = EnumType.STRING)
	private AccessionType accessionType;

	@Column(name = "accession_status")
	@Enumerated(value = EnumType.STRING)
	private AccessionStatus accessionStatus;

	@OneToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;


	public AccessionResponseDto toResponseDto() {
		return AccessionResponseDto.builder().id(id).accessionType(accessionType).accessionStatus(accessionStatus)
				.member(member).group(group).build();
	}
}
