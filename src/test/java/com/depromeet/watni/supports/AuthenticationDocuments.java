package com.depromeet.watni.supports;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationDocuments {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 로그인() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("grant_type", "password");
        requestBody.put("username", "test@naver.com");
        requestBody.put("password", "test");

        ResultActions result = mockMvc.perform(post("/oauth/token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(httpBasic("foo","bar"))
                    .content(requestBody.toString())
                    .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("generate_token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("value is client_id:client_secrect to base64 Encoding")
                        ),
                        requestFields(
                                fieldWithPath("grant_type").description("only supports PASSWORD"),
                                fieldWithPath("username").description("user email"),
                                fieldWithPath("password").description("user password")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("available for 30 minutes access token"),
                                fieldWithPath("token_type").description("token type"),
                                fieldWithPath("refresh_token").description("available for 14 days refresh token"),
                                fieldWithPath("expires_in").description("access token expired seconds"),
                                fieldWithPath("scope").description("not used now")
                        )
                ));
    }

    @Test
    public void 토큰_재발행() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("grant_type", "password");
        requestBody.put("username", "test@naver.com");
        requestBody.put("password", "test");

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("foo","bar"))
                .content(requestBody.toString())
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString);
        String refreshToken = (String) jsonObject.get("refresh_token");
        requestBody = new JSONObject();
        requestBody.put("grant_type", "refresh_token");
        requestBody.put("refresh_token", refreshToken);

        result = mockMvc.perform(post("/oauth/token")
                .content(requestBody.toString())
                .with(httpBasic("foo", "bar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("refresh_token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("value is client_id:client_secrect to base64 Encoding")
                        ),
                        requestFields(
                                fieldWithPath("grant_type").description("only supports refresh_token"),
                                fieldWithPath("refresh_token").description("user refresh_token")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("available for 30 minutes access token"),
                                fieldWithPath("token_type").description("token type"),
                                fieldWithPath("refresh_token").description("available for 14 days refresh token"),
                                fieldWithPath("expires_in").description("access token expired seconds"),
                                fieldWithPath("scope").description("not used now")
                        )
                ));
    }
}
