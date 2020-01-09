package com.depromeet.watni.supports;

import com.depromeet.watni.domain.member.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationDocuments {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void 로그인() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "test@naver.com");
        params.add("password", "test");

        ResultActions result = mockMvc.perform(post("/oauth/token")
                    .params(params)
                    .with(httpBasic("foo", "bar"))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                    .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        result.andExpect(status().isOk())
                .andDo(document("generate_token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("value is client_id:client_secrect to base64 Encoding")
                        ),
                        requestParameters(
                                parameterWithName("grant_type").description("only supports PASSWORD"),
                                parameterWithName("username").description("user email"),
                                parameterWithName("password").description("user password")
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
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "test@naver.com");
        params.add("password", "test");

        ResultActions result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("foo", "bar"))
                .content(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString);
        String refreshToken = (String) jsonObject.get("refresh_token");
        params.clear();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);

        result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("foo", "bar"))
                .content(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        ).andExpect(status().isOk());

        result.andExpect(status().isOk())
                .andDo(document("refresh_token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("value is client_id:client_secrect to base64 Encoding")
                        ),
                        requestParameters(
                                parameterWithName("grant_type").description("only supports refresh_token"),
                                parameterWithName("refresh_token").description("user refresh_token")
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
