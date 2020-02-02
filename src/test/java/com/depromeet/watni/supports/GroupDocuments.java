package com.depromeet.watni.supports;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.group.dto.GroupDto;
import com.depromeet.watni.domain.group.service.GroupGenerateService;
import com.depromeet.watni.domain.group.service.GroupService;
import com.depromeet.watni.domain.member.MemberDetail;
import com.depromeet.watni.domain.member.repository.MemberRepository;
import org.json.JSONObject;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class GroupDocuments {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupGenerateService groupGenerateService;
	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void 그룹만들기() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("groupName", "testGroup");
		requestBody.put("description", "testcode1234");

		ResultActions result = mockMvc.perform(
				post("/api/group")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody.toString())
						.header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
						.accept(MediaType.APPLICATION_JSON_VALUE)
		).andExpect(status().isCreated());

		result.andExpect(status().isCreated())
		.andDo(document("group_create",
				getDocumentRequest(),
				getDocumentResponse(),
				requestHeaders(
						headerWithName("Authorization").description("user accessToken")
				),
				requestFields(
						fieldWithPath("groupName").type(JsonFieldType.STRING).description("그룹이름"),
						fieldWithPath("description").type(JsonFieldType.STRING).description("그룹 설명")
				),
				responseFields(
						fieldWithPath("groupId").type(JsonFieldType.NUMBER).description("그룹id"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("그룹이름"),
						fieldWithPath("conferences.[]").type(JsonFieldType.ARRAY).optional().description("세션"),
						fieldWithPath("accessions.[]").type(JsonFieldType.ARRAY).optional().description("참여 목록")
				)
		));
	}

	@Test
	public void 그룹_조회() throws Exception {
		GroupDto groupDto = GroupDto
				.builder()
				.groupName("testGroup")
				.description("testDescription")
				.build();

		MemberDetail memberDetail = new MemberDetail(memberRepository.findById(1L).get());
		Group group = groupGenerateService.createGroup(groupDto, memberDetail);

		ResultActions resultActions = this.mockMvc.perform(
				get("/api/group/{groupId}", group.getGroupId())
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", ApiDocumentUtils.getAuthorizationHeader(this.mockMvc))
		).andExpect(status().isOk());

		resultActions.andExpect(status().isOk())
				.andDo(document("group_search",
						getDocumentRequest(),
						getDocumentResponse(),
						requestHeaders(
								headerWithName("Authorization").description("user accessToken")
						),
						responseFields(
								fieldWithPath("groupId").type(JsonFieldType.NUMBER).description("그룹id"),
								fieldWithPath("name").type(JsonFieldType.STRING).description("그룹이름"),
								fieldWithPath("conferences.[]").type(JsonFieldType.ARRAY).optional().description("세션"),
								fieldWithPath("accessions.[]").type(JsonFieldType.ARRAY).optional().description("참여 목록")
						)
				));
	}
}