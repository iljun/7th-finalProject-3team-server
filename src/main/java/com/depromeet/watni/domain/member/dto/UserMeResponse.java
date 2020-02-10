package com.depromeet.watni.domain.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMeResponse {
    private String email;
    private String name;
    private List<MemberInfoResponse> memberDetails;

    public UserMeResponse(String email, String name, List<MemberInfoResponse> memberDetails){
        this.email = email;
        this.name = name;
        this.memberDetails = memberDetails;
    }
}
