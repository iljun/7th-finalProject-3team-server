package com.depromeet.watni.domain.conference;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.conference.dto.ConferenceRequestDto;
import com.depromeet.watni.domain.conference.service.ConferenceService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ConferenceServiceTest {

    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupGenerateService groupGenerateService;
    @Autowired
    private MemberRepository memberRepository;
    // TODO group api 변경에 따라 재 작성

    @Test
    public void 컨퍼런스_등록() throws IOException {
        ConferenceRequestDto conferenceRequestDto = new ConferenceRequestDto("testConference", "test", "공덕 창업 허브센터", null, null, null, null, null);
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        Group group = groupGenerateService.createGroup(groupDto, memberDetail);
        Conference conference = conferenceService.generateConference(conferenceRequestDto, group);
        Assert.assertNotNull(conference);
        Assert.assertNotNull(conference.getGroup());
    }

    @Test
    public void 컨퍼런스_수정() throws IOException {
        ConferenceRequestDto conferenceRequestDto = new ConferenceRequestDto("testConference", "test", "공덕 창업 허브센터", null, null, null, null, null);
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        Group group = groupGenerateService.createGroup(groupDto, memberDetail);
        Conference conference = conferenceService.generateConference(conferenceRequestDto, group);
        conferenceRequestDto = new ConferenceRequestDto("testConference_edit", "edit", "공덕 창업 허브센터", null, null, null, null, null);
        Conference updateConference = conferenceService.updateConference(conferenceRequestDto, conference);
        Assert.assertNotNull(updateConference);
        Assert.assertNotNull(updateConference.getGroup());
    }

    @Test
    public void 컨퍼런스_삭제() throws IOException {
        ConferenceRequestDto conferenceRequestDto = new ConferenceRequestDto("testConference", "test", "공덕 창업 허브센터", null, null, null, null, null);
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        Group group = groupGenerateService.createGroup(groupDto, memberDetail);
        Conference conference = conferenceService.generateConference(conferenceRequestDto, group);

        conferenceService.deleteConference(conference);
        group = groupService.getGroup(group.getGroupId());
        Assert.assertTrue(CollectionUtils.isEmpty(group.getConferences()));
    }
}
