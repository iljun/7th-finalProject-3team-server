package com.depromeet.watni.domain.manager;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.depromeet.watni.domain.group.Group;
import com.depromeet.watni.domain.group.GroupService;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.groupcode.GroupCode;
import com.depromeet.watni.domain.groupcode.GroupCodeService;
import com.depromeet.watni.domain.member.Member;
import com.depromeet.watni.supports.SampleData;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ManagerServiceTest {
	@Autowired
	ManagerService managerService;
	@Autowired
	GroupService groupService;
	@Autowired
	SampleData sampleData;

	@Test
	public void 매니저_등록_확인() {
		Member member = sampleData.createMember();
		Group group = sampleData.createGroup();
		Manager manager = managerService.registerManager(group.getGroupId(), member.getMemberId());
		Assert.assertNotNull(manager);
		managerService.checkManager(group.getGroupId(), member.getMemberId());
	}
}