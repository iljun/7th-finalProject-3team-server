package com.depromeet.watni.supports;

import io.micrometer.core.instrument.util.IOUtils;
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
import java.nio.charset.Charset;

import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentRequest;
import static com.depromeet.watni.supports.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
public class MemberDocuments {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void post_member() throws Exception {

        String body = readJson("member-signup.json");

        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/sign-up")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("POST_member",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("unique email"),
                                fieldWithPath("password").description("password"),
                                fieldWithPath("name").description("username")
                        )
                ));
    }

    private String readJson(final String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
    }
}
