//package com.depromeet.watni.domain.manager;
//
//import com.depromeet.watni.domain.group.domain.Group;
//import com.depromeet.watni.domain.group.service.GroupService;
//import com.depromeet.watni.domain.manager.domain.Manager;
//import com.depromeet.watni.domain.manager.service.ManagerService;
//import com.depromeet.watni.domain.member.domain.Member;
//import com.depromeet.watni.supports.SampleData;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//public class ManagerServiceTest {
//	@Autowired
//	ManagerService managerService;
//	@Autowired
//	GroupService groupService;
//	@Autowired
//	SampleData sampleData;
//
//	@Test
//	public void 매니저_등록_확인() {
//		Member member = sampleData.createMember();
//		Group group = sampleData.createGroup();
//		Manager manager = managerService.registerManager(group.getGroupId(), member.getMemberId());
//		Assert.assertNotNull(manager);
//		managerService.checkManager(group.getGroupId(), member.getMemberId());
//	}
//}