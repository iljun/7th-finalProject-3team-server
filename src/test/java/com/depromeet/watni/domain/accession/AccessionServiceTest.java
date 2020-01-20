//package com.depromeet.watni.domain.accession;
//
//import com.depromeet.watni.domain.accession.domain.Accession;
//import com.depromeet.watni.domain.accession.service.AccessionService;
//import com.depromeet.watni.domain.group.domain.Group;
//import com.depromeet.watni.domain.group.service.GroupService;
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
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//public class AccessionServiceTest {
//	@Autowired
//	AccessionService accessionService;
//	@Autowired
//	GroupService groupService;
//	@Autowired
//	SampleData sampleData;
//
//	@Test
//	public void 관리자로_모임참가() {
//		Member member = sampleData.createMember();
//		Group group = sampleData.createGroup();
//		List<Long> memberIdList = new ArrayList<Long>();
//		memberIdList.add(member.getMemberId());
//		List<Accession> accessions = accessionService.accessGroupByCode(group.getGroupId(), memberIdList);
//		Assert.assertNotNull(accessions);
//		Assert.assertEquals(accessions.get(0).getGroup(), group);
//		Assert.assertEquals(accessions.get(0).getMember(), member);
//
//	}
//}
