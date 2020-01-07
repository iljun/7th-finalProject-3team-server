package com.depromeet.watni.domain.group;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.depromeet.watni.domain.group.Group;
import com.depromeet.watni.domain.group.GroupService;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.member.Member;
import com.depromeet.watni.supports.SampleData;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class GroupServiceTest {
	@Autowired
	GroupService groupService;

	@Test
	public void 그룹_만들기() {
		GroupDto groupDto = GroupDto.builder().code("testcode").groupName("테스트그룹").build();
		Group group = groupService.createGroup(groupDto);
		Assert.assertNotNull(group);
	}
}
