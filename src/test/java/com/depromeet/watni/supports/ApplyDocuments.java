package com.depromeet.watni.supports;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.member.MemberDetail;
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

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

    private Group group;

    @Before
    public void setup() {
        GroupDto groupDto = GroupDto
                .builder()
                .description("test")
                .groupName("testGroup")
                .build();
        MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
        group = groupGenerateService.createGroup(groupDto, memberDetail);
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

    }
}
