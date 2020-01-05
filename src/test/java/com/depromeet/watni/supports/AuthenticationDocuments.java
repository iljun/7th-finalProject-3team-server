package com.depromeet.watni.supports;

import org.apache.commons.io.IOUtils;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
        String body = readJson("signin.json");


        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/sign-in")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("sign-in",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("unique email"),
                                fieldWithPath("password").description("password"),
                                fieldWithPath("grantType").description("only supports PASSWORD"),
                                fieldWithPath("clientId").description("clientId"),
                                fieldWithPath("clientSecret").description("clientSecret")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("available for 30 minutes access token"),
                                fieldWithPath("refreshToken").description("available for 14 days refresh token")
                        )
                ));
    }

    private String readJson(final String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return IOUtils.toString(resource.getInputStream(), "UTF-8");
    }
}
