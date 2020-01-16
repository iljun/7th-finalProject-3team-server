package com.depromeet.watni.supports;

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class GroupDocuments {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void 그룹만들기() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("groupName", "testGroup");
		requestBody.put("code", "testcode1234");

		ResultActions result = mockMvc.perform(post("/api/groups").contentType(MediaType.APPLICATION_JSON)
				.content(requestBody.toString()).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
		result.andExpect(status().isOk())
		.andDo(document("group_create",
				getDocumentRequest(),
				getDocumentResponse(),
				requestFields(
						fieldWithPath("groupName").type(JsonFieldType.STRING).description("그룹이름"),
						fieldWithPath("code").type(JsonFieldType.STRING).description("그룹 참여 코드")
						),
				responseFields(
						fieldWithPath("groupId").type(JsonFieldType.NUMBER).description("그룹id"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("그룹이름"),
						fieldWithPath("conferences").type(JsonFieldType.ARRAY).optional().description("세션"),
						fieldWithPath("code").type(JsonFieldType.STRING).optional().description("그룹 참여 코드")
						)
				)
				);
	}

}