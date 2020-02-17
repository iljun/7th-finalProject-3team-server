package com.depromeet.watni.supports;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.service.CodeApplyService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.domain.member.dto.MemberRequestDto;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import com.depromeet.watni.domain.member.service.MemberService;
import org.json.JSONArray;
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

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class AccessionDocuments {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupGenerateService groupGenerateService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CodeApplyService codeApplyService;

    private Member member;

    private Group group;

    @Before
    public void setup() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("test1@gmail.com","test1","test1");
        member = memberService.createMember(memberRequestDto);
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        group = groupGenerateService.createGroup(groupDto, memberDetail);

        BaseApplyRequestDto baseApplyRequestDto = new BaseApplyRequestDto();
        baseApplyRequestDto.setApplyType(ApplyType.CODE);
        baseApplyRequestDto.setContent("participateCode");
        codeApplyService.generateApply(baseApplyRequestDto,group);
    }

    @Test
    public void 그룹_참가() throws Exception {
        JSONArray memberIdList = new JSONArray();
        memberIdList.put(member.getMemberId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberIdList", memberIdList);
        jsonObject.put("code","participateCode");

        ResultActions result = this.mockMvc.perform(
                post("/api/group/accession", group.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isAccepted());

        result.andExpect(status().isAccepted())
                .andDo(document("POST_ACCESS_GROUP",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("user accessToken")
                        ),
                        requestFields(
                                fieldWithPath("memberIdList").description("memberId List"),
                                fieldWithPath("groupId").type(JsonFieldType.NUMBER).description("groupId").optional(),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("Group code").optional()
                        ),
                        responseFields(
                                fieldWithPath("groupId").type(JsonFieldType.NUMBER).optional().description("그룹id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).optional().description("그룹이름"),
                                fieldWithPath("conferences").type(JsonFieldType.ARRAY).optional().description("모임"),
                                fieldWithPath("conferences[].conferenceId").type(JsonFieldType.NUMBER).optional().description("conference Id"),
                                fieldWithPath("conferences[].name").type(JsonFieldType.STRING).optional().description("conference name"),
                                fieldWithPath("conferences[].description").type(JsonFieldType.STRING).optional().description("conference description"),
                                fieldWithPath("conferences[].locationInfo").type(JsonFieldType.STRING).optional().description("conference locationInfo"),
                                fieldWithPath("conferences[].startAt").type(JsonFieldType.NUMBER).optional().description("conference startAt"),
                                fieldWithPath("conferences[].endAt").type(JsonFieldType.NUMBER).optional().description("conference endAt"),
                                fieldWithPath("conferences[].photoUrl").type(JsonFieldType.STRING).optional().description("conference photoUrl"),
                                fieldWithPath("conferences[].notice").type(JsonFieldType.STRING).optional().description("conference notice")
                        )
                        )
                );
    }
}
