package com.depromeet.watni.supports;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.conference.dto.ConferenceRequestDto;
import com.depromeet.watni.domain.conference.service.ConferenceService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class MemberDocuments {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GroupGenerateService groupGenerateService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private MemberRepository memberRepository;

    private Group group;
    private Conference conference;


    @Before
    public void setUp() throws IOException  {
        ConferenceRequestDto conferenceRequestDto = new ConferenceRequestDto("testConference1", "test1", "공덕 창업 허브센터1", null, null, null, null, null);
        GroupDto groupDto = GroupDto
                .builder()
                .groupName("testGroup1")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        group = groupGenerateService.createGroup(groupDto, memberDetail);
        conference = conferenceService.generateConference(conferenceRequestDto, group);
    }

    @Test
    public void post_member() throws Exception {

        String body = readJson("member-signup.json");

        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("POST_member",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("unique email"),
                                fieldWithPath("password").description("password"),
                                fieldWithPath("name").description("username")
                        )
                ));
    }

    @Test
    public void user_me() throws Exception {

        //MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        ResultActions resultActions = this.mockMvc.perform(
                get("/api/user/me")
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        resultActions.andExpect(status().isOk())
                .andDo(document("USER_ME",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("user accessToken")
                        ),
                        responseFields(

                                fieldWithPath("email").type(JsonFieldType.STRING).description("멤버 이메일"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("멤버 이름"),
                                fieldWithPath("memberDetails").type(JsonFieldType.ARRAY).optional().description("멤버의 참여한 모임 정보"),
                                fieldWithPath("memberDetails[].group.groupId").type(JsonFieldType.NUMBER).optional().description("그룹id"),
                                fieldWithPath("memberDetails[].group.name").type(JsonFieldType.STRING).optional().description("그룹이름"),
                                fieldWithPath("memberDetails[].group.conferences").type(JsonFieldType.ARRAY).optional().description("모임"),
                                fieldWithPath("memberDetails[].group.conferences[].conferenceId").type(JsonFieldType.NUMBER).optional().description("conference Id"),
                                fieldWithPath("memberDetails[].group.conferences[].name").type(JsonFieldType.STRING).optional().description("conference name"),
                                fieldWithPath("memberDetails[].group.conferences[].description").type(JsonFieldType.STRING).optional().description("conference description"),
                                fieldWithPath("memberDetails[].group.conferences[].locationInfo").type(JsonFieldType.STRING).optional().description("conference locationInfo"),
                                fieldWithPath("memberDetails[].group.conferences[].startAt").type(JsonFieldType.NUMBER).optional().description("conference startAt"),
                                fieldWithPath("memberDetails[].group.conferences[].endAt").type(JsonFieldType.NUMBER).optional().description("conference endAt"),
                                fieldWithPath("memberDetails[].group.conferences[].photoUrl").type(JsonFieldType.STRING).optional().description("conference photoUrl"),
                                fieldWithPath("memberDetails[].group.conferences[].notice").type(JsonFieldType.STRING).optional().description("conference notice"),
                                fieldWithPath("memberDetails[].manager").type(JsonFieldType.BOOLEAN).description("isManger")
                        )
                        )
                );
    }

    private String readJson(final String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
    }
}
