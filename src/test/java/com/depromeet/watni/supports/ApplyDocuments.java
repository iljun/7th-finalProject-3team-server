package com.depromeet.watni.supports;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import com.depromeet.watni.domain.apply.dto.BaseApplyRequestDto;
import com.depromeet.watni.domain.apply.repository.CodeApplyRepository;
import com.depromeet.watni.domain.apply.service.CodeApplyService;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import org.json.JSONObject;
import org.junit.After;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class ApplyDocuments {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupGenerateService groupGenerateService;

    @Autowired
    private CodeApplyService codeApplyService;

    @Autowired
    private CodeApplyRepository codeApplyRepository;

    private Group group;
    private Group group1;

    @Before
    public void setup() {
        BaseApplyRequestDto baseApplyRequestDto = new BaseApplyRequestDto();
        baseApplyRequestDto.setApplyType(ApplyType.CODE);
        baseApplyRequestDto.setContent("getCode");
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        group = groupGenerateService.createGroup(groupDto, memberDetail);
        group1 = groupGenerateService.createGroup(groupDto,memberDetail);
        codeApplyService.generateApply(baseApplyRequestDto,group1);
    }

    @After
    public void finish(){
        codeApplyRepository.deleteAll();
    }

    @Test
    public void 코드_생성() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("applyType", "CODE");
        jsonObject.put("content", "CCCCC");

        ResultActions result = this.mockMvc.perform(
                post("/api/group/{groupId}/apply-way", group.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isCreated());

        result.andExpect(status().isCreated())
                .andDo(document("POST_APPLY_WAY",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("user accessToken")
                        ),
                        pathParameters(
                                parameterWithName("groupId").description("group Id")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code")
                        )
                        )
                );
    }

    @Test
    public void 코드_조회() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("applyType", "CODE");

        ResultActions result = this.mockMvc.perform(
                get("/api/group/{groupId}/apply-way", group1.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("GET_APPLY_WAY",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("user accessToken")
                        ),
                        pathParameters(
                                parameterWithName("groupId").description("group Id")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code")
                        )
                        )
                );

    }

    @Test
    public void 코드_중복_체크() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("applyType", "CODE");
        jsonObject.put("content", "newCode");

        ResultActions result = this.mockMvc.perform(
                get("/api/group/{groupId}/apply-way/check", group1.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isAccepted());

        result.andExpect(status().isAccepted())
                .andDo(document("CHECK_APPLY_CODE",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("user accessToken")
                        ),
                        pathParameters(
                                parameterWithName("groupId").description("group Id")
                        )
                        )
                );

    }

    @Test
    public void 코드_그룹_참여() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("applyType", "CODE");
        jsonObject.put("content", "getCode");

        ResultActions result = this.mockMvc.perform(
                post("/api/group/apply-way/confirm", group1.getGroupId())
                        .header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString())
        ).andExpect(status().isAccepted());

        result.andExpect(status().isAccepted())
                .andDo(document("CONFIRM_APPLY_CODE_CORRECT",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("user accessToken")
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
