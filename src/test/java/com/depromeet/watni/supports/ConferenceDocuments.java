package com.depromeet.watni.supports;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.conference.dto.ConferenceRequestDto;
import com.depromeet.watni.domain.conference.service.ConferenceService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.manager.service.ManagerService;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class ConferenceDocuments {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ManagerService managerService;

    private Group group;
    private Conference conference;

    @Before
    public void setUp() {
        ConferenceRequestDto conferenceRequestDto = new ConferenceRequestDto("testConference", "test", "공덕 창업 허브센터", null, null, null);
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        group = groupService.createGroup(groupDto);
        conference = conferenceService.generateConference(conferenceRequestDto, group);
        Member member = memberRepository.findByEmail("test@naver.com").get();
        managerService.registerManager(group, member);
    }

    @Test
    public void 컨퍼런스_조회() throws Exception {
        ResultActions result = this.mockMvc.perform(
                get("/api/group/{groupId}/conferences", group.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("GET_CONFERENCES",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("groupId").description("group Id")
                        ),
                        responseFields(
                                fieldWithPath("[].conferenceId").type(JsonFieldType.NUMBER).description("conference Id"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("conference name"),
                                fieldWithPath("[].description").type(JsonFieldType.STRING).optional().description("conference description"),
                                fieldWithPath("[].locationInfo").type(JsonFieldType.STRING).optional().description("conference locationInfo"),
                                fieldWithPath("[].startAt").type(JsonFieldType.NUMBER).optional().description("conference startAt"),
                                fieldWithPath("[].endAt").type(JsonFieldType.NUMBER).optional().description("conference endAt")
                        )
                        )
                );
    }

    @Test
    public void 특정_컨퍼런스_조회() throws Exception {
        ResultActions result = this.mockMvc.perform(
                get("/api/group/{groupId}/conference/{conferenceId}", group.getGroupId(), conference.getConferenceId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("GET_CONFERENCE",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("groupId").description("group Id"),
                                parameterWithName("conferenceId").description("conference Id")
                        ),
                        responseFields(
                                fieldWithPath("conferenceId").type(JsonFieldType.NUMBER).description("conference Id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("conference name"),
                                fieldWithPath("description").type(JsonFieldType.STRING).optional().description("conference description"),
                                fieldWithPath("locationInfo").type(JsonFieldType.STRING).optional().description("conference locationInfo"),
                                fieldWithPath("startAt").type(JsonFieldType.NUMBER).optional().description("conference startAt"),
                                fieldWithPath("endAt").type(JsonFieldType.NUMBER).optional().description("conference endAt")
                        )
                        )
                );
    }

    @Test
    public void 컨퍼런스_등록() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test");
        jsonObject.put("description", "test");
        jsonObject.put("locationInfo", "우리집");
        LocalDateTime localDateTime = LocalDateTime.now();
        jsonObject.put("startAt", Timestamp.valueOf(localDateTime).getTime());
        localDateTime = localDateTime.plusDays(1);
        jsonObject.put("endAt", Timestamp.valueOf(localDateTime).getTime());

        ResultActions result = this.mockMvc.perform(
                post("/api/group/{groupId}/conference", group.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isOk());

        System.out.println(result.toString());
        result.andExpect(status().isOk())
                .andDo(document("POST_CONFERENCE",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("groupId").description("group Id")
                        ),
                        responseFields(
                                fieldWithPath("conferenceId").type(JsonFieldType.NUMBER).description("conference Id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("conference name"),
                                fieldWithPath("description").type(JsonFieldType.STRING).optional().description("conference description"),
                                fieldWithPath("locationInfo").type(JsonFieldType.STRING).optional().description("conference locationInfo"),
                                fieldWithPath("startAt").type(JsonFieldType.NUMBER).description("conference startAt"),
                                fieldWithPath("endAt").type(JsonFieldType.NUMBER).description("conference endAt")
                        )
                        )
                );
    }

    @Test
    public void 컨퍼런스_수정() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test");
        jsonObject.put("description", "test");
        jsonObject.put("locationInfo", "우리집");
        ResultActions result = this.mockMvc.perform(
                patch("/api/group/{groupId}/conference/{conferenceId}", group.getGroupId(), conference.getConferenceId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("PATCH_CONFERENCE",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("groupId").description("group Id"),
                                parameterWithName("conferenceId").description("conference Id")
                        ),
                        responseFields(
                                fieldWithPath("conferenceId").type(JsonFieldType.NUMBER).description("conference Id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("conference name"),
                                fieldWithPath("description").type(JsonFieldType.STRING).optional().description("conference description"),
                                fieldWithPath("locationInfo").type(JsonFieldType.STRING).optional().description("conference locationInfo"),
                                fieldWithPath("startAt").type(JsonFieldType.NUMBER).optional().description("conference startAt"),
                                fieldWithPath("endAt").type(JsonFieldType.NUMBER).optional().description("conference endAt")
                        )
                        )
                );
    }


    @Test
    public void 컨퍼런스_삭제() throws Exception {
        ResultActions result = this.mockMvc.perform(
                delete("/api/group/{groupId}/conference/{conferenceId}", group.getGroupId(), conference.getConferenceId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isAccepted());

        result.andExpect(status().isAccepted())
                .andDo(document("DELETE_CONFERENCE",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("groupId").description("group Id"),
                                parameterWithName("conferenceId").description("conference Id")
                        )
                        )
                );
    }
}
