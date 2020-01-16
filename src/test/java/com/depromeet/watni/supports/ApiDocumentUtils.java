package com.depromeet.watni.supports;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .scheme("http")
                        .host("ec2-52-78-36-242.ap-northeast-2.compute.amazonaws.com")
                        .port(80)
                        .removePort(),
                prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    static String getAuthorizationHeader(MockMvc mockMvc) throws Exception {
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
        String accessToken = (String) jsonObject.get("access_token");
        return "Bearer " + accessToken;
    }

    static final HeaderDescriptor[] AUTHORIZATION_HEADER = new HeaderDescriptor[] {
        headerWithName("Authorization").description("Bearer ${accessToken}"),
    };
}
