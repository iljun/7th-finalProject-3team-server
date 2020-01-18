package com.depromeet.watni.domain.groupcode;

import com.depromeet.watni.domain.group.domain.Group;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public class GroupCode {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeId;
	@OneToOne
	@JoinColumn(name = "group_id")
	private Group group;
	private String code;
}