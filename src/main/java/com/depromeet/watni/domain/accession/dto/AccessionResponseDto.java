package com.depromeet.watni.domain.accession.dto;

import com.depromeet.watni.domain.accession.constant.AccessionStatus;
import com.depromeet.watni.domain.accession.constant.AccessionType;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.member.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessionResponseDto {
	private long id;

	private AccessionType accessionType;

	private AccessionStatus accessionStatus;

	private Member member;

	private Group group;

}
